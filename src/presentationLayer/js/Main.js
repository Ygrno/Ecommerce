
function logout() {
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

function ViewCart(products) {
    /**display cart popup**/

    let productList = products["productsInCart"];
    if (productList.length == 0) return;
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
    let credit_card_number = document.createElement("input");
    let expire_date = document.createElement("input");
    let cvv = document.createElement("input");


    [name, credit_card_number, expire_date, cvv].forEach((d)=>{
       d.className = 'text_input';
       div.appendChild(d);
       div.style.cssFloat = 'left';
    });

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

}

let instance = new Main();
