
function logout() {
    instance.sign_out(localStorage.getItem("current_username"));
    localStorage.removeItem("current_username");
    localStorage.removeItem("guest");
    localStorage.removeItem("guest_id");
    window.location.href = "main.html";
}

function home(){
    window.location.href = "home.html";
}


function searchProduct(ele){
    if(event.key === 'Enter') {
        let product = ele.value;
        window.location.href = "products.html?p="+product;
    }

}

function newNotification(){
    alert("YOU HAVE NEW NOTIFICATION!");
}

function popUp(content_div) {
    let modal_pop_up = document.getElementById("modal_pop_up");
    modal_pop_up.style.display = "block";
    let modal_content = document.getElementById("modal_content");
    if(modal_content === null) return;
    modal_content.appendChild(content_div);

}

function clearPopUp(){
    let modal_content = document.getElementById("modal_content");
    if(modal_content === null) return;
    modal_content.innerHTML = '';
}

function closePOP() {
    let modal_pop_up = document.getElementById("modal_pop_up");
    if (modal_pop_up === null) return;
    let modal_content = document.getElementById("modal_content");
    modal_content.innerHTML = '';
    modal_pop_up.style.display = "none";
}

function includeHeader() {
    var z, i, elmnt, file, xhttp;
    /* Loop through a collection of all HTML elements: */
    z = document.getElementsByTagName("*");
    for (i = 0; i < z.length; i++) {
        elmnt = z[i];
        /*search for elements with a certain atrribute:*/
        file = elmnt.getAttribute("w3-include-html");
        if (file) {
            /* Make an HTTP request using the attribute value as the file name: */
            xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function() {
                if (this.readyState == 4) {
                    if (this.status == 200) {elmnt.innerHTML = this.responseText;}
                    if (this.status == 404) {elmnt.innerHTML = "Page not found.";}
                    /* Remove the attribute, and call this function once more: */
                    elmnt.removeAttribute("w3-include-html");
                    includeHeader();
                }
            };
            xhttp.open("GET", file, false);
            xhttp.send();
            setLoggedUserLabel();
        }
    }
}

function setLoggedUserLabel() {
    let current_username = localStorage.getItem("current_username");
    document.getElementById("logged_as").innerHTML = current_username;
}

function isGuest(){
    let b=localStorage.getItem("guest");
    return b==="true";
}


class Main{

    static getInstance(){
        return instance;
    }

    constructor(){

    }

    getUrlVars() {
        var vars = {};
        var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
            vars[key] = value;
        });
        return vars;
    }

    init(onopen){
        //init client
        if(this.client !== undefined)
            return;

        let client = new EcommerceClient("127.0.0.1", 8112);
        let that = this;

        //Add listeners
        client.addMessageListener((msg)=>that.messageHandler(msg));

        //set open call back
        client.setOpenCallBack(()=>{
            console.log("Connection is opened");
            if(onopen)
                onopen();
        });

        this.client = client;

        //start client
        client.start();

    }

    messageHandler(msg) {
        let response = JSON.parse(msg);
        console.log(response);
    }

    login(username, password){
        this.client.send(JSON.stringify({req:"login", username:username, password:password}));
    }

    sendToServer(request){
        this.client.send(JSON.stringify(request));
    }

    continueAsAGuest(username, password){
        this.client.send(JSON.stringify({req:"continue_as_a_guest"}));
    }

    get_products(name){
        this.client.send(JSON.stringify({req:"get_products", name:name}));
    }


    get_stores(){
        this.client.send(JSON.stringify({req:"get_stores"}));
    }

    get_store(store){
        this.client.send(JSON.stringify({req:"get_store", store:store}));
    }

    get_products_of_store(store){
        this.client.send(JSON.stringify({req:"get_product_of_store", store:store}));

    }

    Signup(username, password){
        this.client.send(JSON.stringify({req:"signup", username:username, password:password}));
    }

    saveProduct(storeName,productName){
        const guestSaveProduct=()=>{
            let id=localStorage.getItem("guest_id");
            this.client.send(JSON.stringify({req:"save_product_for_guest",id:id,store:storeName,product:productName}));
        };

        const SubscriberSaveProduct=()=>{
            let username = localStorage.getItem("current_username");
            this.client.send(JSON.stringify({req:"save_product_for_subscriber",username:username,store:storeName,product:productName}));
        };

        if(isGuest()){
            guestSaveProduct();
        }else{
            SubscriberSaveProduct();
        }

    }

    viewCart(){
        const viewGuestCart = ()=>{
            let id = localStorage.getItem("guest_id");
            this.client.send(JSON.stringify({req:"view_cart_guest",id:id}));
        };

        const viewSubscriberCart = ()=>{
            let username = localStorage.getItem("current_username");
            this.client.send(JSON.stringify({req:"view_cart_subscriber",username:username}));
        };

        if(isGuest()){
            viewGuestCart();
        }else {
            viewSubscriberCart();
        }
    }



    send_process_details(details) {
        let msg = {
            buyer_name: details.buyer_name,
            creditCardNumber: details.creditCardNumber,
            expireDate: details.expireDate,
            cvv: details.cvv,
            discount: details.discount
        };
        if (isGuest()) {
            msg["id"] = localStorage.getItem("guest_id");
            msg["req"] = "buy_cart_guest";
        } else {
            msg["username"] = localStorage.getItem("current_username");
            msg["req"] = "buy_cart_subscriber";
        }

        this.client.send(JSON.stringify(msg));
    }

    sign_out(userName){
        if(!isGuest())
            this.client.send(JSON.stringify({req:"sign_out",username:userName}))
    }

    openStore(store_name){
        if(!isGuest())
            this.client.send(JSON.stringify({req:"open_store",username:localStorage.getItem("current_username"),store_name:store_name}));
    }

    viewPusrchaseHistory(){
        if(!isGuest()){
            this.client.send(JSON.stringify({req:"view_purchase_history",username:localStorage.getItem("current_username")}));
        }
    }

    sendStoreReview(details){
        this.client.send(JSON.stringify(details));
    }

    add_store_product(details){
        let msg = {
            req: details.req,
            user_name: details.user_name,
            store_name: details.store_names,
            product: details.product_names,
            price: details.product_price,
            amount: details.product_amount
        };
        console.log(JSON.stringify(msg));
        this.client.send(JSON.stringify(msg));
    }

    remove_store_product(details){

        this.client.send(JSON.stringify(details));
    }

    edit_store_product(details){
        this.client.send(JSON.stringify(details));
    }

    assign_store_manager(details){
        this.client.send(JSON.stringify(details));
    }

    remove_store_manager(details){
        this.client.send(JSON.stringify(details));
    }

    assign_store_owner(details){
        this.client.send(JSON.stringify(details));
    }

    close_store(details){
        this.client.send(JSON.stringify(details));
    }

    watch_store_history(details){
        this.client.send(JSON.stringify(details));
    }

    add_store_visible_discount(details){
        this.client.send(JSON.stringify(details));
    }

    get_notifications(){
        this.client.send(JSON.stringify({req:"get_notifications",username:localStorage.getItem("current_username")}));
    }

    get_user_permissions(store, user){
        this.client.send(JSON.stringify({req:"get_user_permissions",username:user, store:store}));
    }

    view_customer_history(details){
        this.client.send(JSON.stringify(details));
    }


    remove_subscriber(username){
        this.client.send(JSON.stringify({req:"remove_subscriber", username:username}));
    }

    view_history_store(details){
        this.client.send(JSON.stringify(details));
    }

    today_revenue(){
        this.client.send(JSON.stringify({req:"today_revenue"}));
    }

    date_revenue(){
        this.client.send(JSON.stringify(details));
    }
    edit_manager_permissions(store, assigned_user, permissions){
        this.client.send(JSON.stringify({
            req: "edit_manager_permissions",
            user_name: localStorage.getItem("current_username"),
            store_name:store,
            user_assign: assigned_user,
            permissions:permissions
        }));
    }

}

let instance = new Main();