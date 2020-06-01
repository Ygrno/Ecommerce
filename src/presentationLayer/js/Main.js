
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



function buyResponse(response){
    alert(JSON.stringify(response));
}

function openStoreResponse(response) {
    alert(response);
}
function searchProduct(ele){
    if(event.key === 'Enter') {
        let product = ele.value;
        window.location.href = "products.html?p="+product;
    }

}

function ViewCart(products) {
    /**display cart popup**/

    let productList = products["productsInCart"];
    if (productList.length === 0) return;
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let prodcutHTMLList = new HTMLList(div);

    const cellBuilder = (product)=>{
        //DESIGN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        let d = document.createElement("div");
        d.innerHTML = "<b>"+JSON.stringify(product)+"</b>";
        return d;
    };

    productList.forEach((p)=> prodcutHTMLList.addElement(p));
    prodcutHTMLList.render(cellBuilder);

    let BuyButton = document.createElement("div");
    BuyButton.className = "Green_beautiful_div";
    BuyButton.innerHTML = '<b>Buy</b>';
    BuyButton.style.fontSize = '40px';
    BuyButton.style.width = '100px';
    BuyButton.style.marginTop = '3%';
    BuyButton.onclick = function(){
        buyThings(products);
    };

    div.appendChild(BuyButton);


    popUp(div);
}

function buyThings(products){
    clearPopUp();

    let div = document.createElement("div");
    div.style.overflow = 'auto';
    let productList = products["productsInCart"];

    let cost = productList.reduce((acc,curr)=>acc + curr["price"],0);

    let priceDiv = document.createElement("div");
    priceDiv.innerHTML = "Price : "+cost;
    priceDiv.className = 'total_price_div';
    div.appendChild(priceDiv);

    let name = document.createElement("input");
    name.placeholder="Name";
    let credit_card_number = document.createElement("input");
    credit_card_number.placeholder="Credit card number";
    let expire_date = document.createElement("input");
    expire_date.placeholder = "Expire date (mm/yy)";
    let cvv = document.createElement("input");
    cvv.placeholder="cvv";


    [name, credit_card_number, expire_date, cvv].forEach((d)=>{
       d.className = 'text_input';
       div.appendChild(d);
    });

    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Finish</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let buyerName = name.value;
        let ccNumber = credit_card_number.value;
        let expire_date_value = expire_date.value;
        let cvvValue = cvv.value;
        if(buyerName === ""){
            alert("please enter the name");
            return;
        }
        if(ccNumber=== "") {
            alert("please enter the credit card number");
            return;
        }
        if(expire_date_value=== "") {
            alert("please enter the expire date");
            return;
        }
        if(cvvValue=== "") {
            alert("please enter the cvv");
            return;
        }
        instance.send_process_details({
            buyer_name: buyerName,
            creditCardNumber: ccNumber,
            expireDate: expire_date_value,
            cvv: cvvValue,
            discount: 0        ///change later
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function viewPurchaseHistory(products){
    if(isGuest()){
        alert("You should sign in first");
        return;
    }

    let productList = products["products_in_history"];
    if (productList.length === 0) return;
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let prodcutHTMLList = new HTMLList(div);

    const cellBuilder = (product)=>{
        //DESIGN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        let d = document.createElement("div");
        d.innerHTML = "<b>"+JSON.stringify(product)+"</b>";
        return d;
    };

    productList.forEach((p)=> prodcutHTMLList.addElement(p));
    prodcutHTMLList.render(cellBuilder);
    popUp(div);

}


function addStoreReview() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let priceDiv = document.createElement("div");
    priceDiv.innerHTML = "Write review for "+store_name+" store";
    priceDiv.className = 'total_price_div';
    div.appendChild(priceDiv);

    let product_name = document.createElement("input");
    product_name.placeholder="Enter product name";

    let comment = document.createElement("input");
    comment.placeholder="Add your comment on this product";

    let rate = document.createElement("input");
    rate.placeholder="Enter a rating for this product (From 1 to 5)";


    [product_name, comment, rate].forEach((d)=>{
        d.className = 'text_input';
        div.appendChild(d);
    });

    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Send</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let pName = product_name.value;
        let com = comment.value;
        let ra = rate.value;

        if(pName === ""){
            alert("please enter the product name");
            return;
        }
        if(com=== "") {
            alert("please enter the comment");
            return;
        }
        if(ra=== "" || (ra!=="1" && ra!=="2" && ra!=="3" &&ra!=="4" &&ra!=="5" ) ) {
            alert("please enter the rank correctly");
            return;
        }

        instance.send_process_details({
            store_name: store_name,
            product_name: pName,
            review_data: com,
            rank: ra,
        });
    };
    div.appendChild(finish);

    popUp(div);

}

function openNewStore(){
    if(isGuest()){
        alert("You should sign in first");
        return;
    }


    let div = document.createElement("div");
    div.style.overflow = 'auto';
    let name = document.createElement("input");
    name.placeholder="Enter Store Name";
    name.className = 'text_input';
    div.appendChild(name);


    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Finish</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let storeName = name.value;
        if(storeName === ""){
            alert("please enter the store name");
            return;
        }
        instance.openStore(storeName);
    };

    div.appendChild(finish);
    popUp(div);
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
            }
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


function viewCartProduct(product){

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

}

let instance = new Main();
