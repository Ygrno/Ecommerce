
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

function add_store_product_response(response) {
    alert(JSON.stringify(response));
}

function remove_store_product_response(response) {
    alert(JSON.stringify(response));
}

function edit_store_product_response(response) {
    alert(JSON.stringify(response));
}

function assign_store_manager_response(response) {
    alert(JSON.stringify(response));
}

function remove_store_manager_response(response) {
    alert(JSON.stringify(response));
}

function assign_store_owner_response(response) {
    alert(JSON.stringify(response));
}

function remove_store_owner_response(response) {
    alert(JSON.stringify(response));
}

function close_store_response(response) {
    alert(JSON.stringify(response));
}

function openStoreResponse(response) {
    alert(response);
}

function send_query_to_store_response(response) {
    alert(JSON.stringify(response));
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

    productList.innerHTML = productList;
    productList.className = 'total_price_div';
    div.appendChild(productList);
    popUp(div);

}

function askStoreQuestion() {
    if(isGuest()){
        alert("You should sign in first");
        return;
    }

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Ask us a question";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let question = document.createElement("input");
    question.placeholder="Enter yor question";



    [question].forEach((d)=>{
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
        let qes = question.value;

        if(qes === ""){
            alert("please enter the question");
            return;
        }

        instance.sendStoreReview({
            username:localStorage.getItem("current_username"),
            query:qes,
            req:"send_query_to_store"
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function addStoreReview() {
    if(isGuest()){
        alert("You should sign in first");
        return;
    }
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Write review for "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

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

        instance.sendStoreReview({
            username:localStorage.getItem("current_username"),
            store_name: store_name,
            product_name: pName,
            review_data: com,
            rank: ra,
            req:"write_review"
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

function edit_product(){
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Edit a product in "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let product_name = document.createElement("input");
    product_name.placeholder="Enter product name";

    let new_product_name = document.createElement("input");
    new_product_name.placeholder="Enter new product name (optional)";

    let product_price = document.createElement("input");
    product_price.placeholder="Enter the price of the product";

    let product_amount = document.createElement("input");
    product_amount.placeholder="Enter the amount of the product";


    [product_name, new_product_name,product_price, product_amount].forEach((d)=>{
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
        let name = product_name.value;
        let newname = new_product_name.value;
        let price = product_price.value;
        let amount = product_amount.value;

        if(name === ""){
            alert("please enter the product name");
            return;
        }
        if(newname===""){
            newname=name;
        }
        if(price=== "") {
            alert("please enter the price");
            return;
        }
        if(amount=== "") {
            alert("please enter the amount");
            return;
        }

        instance.edit_store_product({
            user_name: localStorage.getItem("current_username"),
            store: store_name,
            product: name,
            newp: newname,
            price: price,
            amount: amount,
            req: "edit_store_product",
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function add_discount() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Add discount";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let productName = document.createElement("input");
    productName.placeholder="Enter product name";
    productName.className = 'text_input';
    div.appendChild(productName);

    let percentage = document.createElement("input");
    percentage.placeholder="Enter discount percentage (from 0 to 1)";
    percentage.className = 'text_input';
    div.appendChild(percentage);

    let percentageDate = document.createElement("input");
    percentageDate.placeholder="Enter discount due date (ddmmyyyy without /)";
    percentageDate.className = 'text_input';
    div.appendChild(percentageDate);

    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Finish</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let product = productName.value;
        if(product === ""){
            alert("please enter the product name");
            return;
        }
        let perc = percentage.value;
        if(perc === ""){
            alert("please enter the product name");
            return;
        }
        let date = percentageDate.value;
        if(date === ""){
            alert("please enter the product name");
            return;
        }
        //add discount type
        instance.add_store_visible_discount({
            user_name: localStorage.getItem("current_username"),
            store: store_name,
            product: product,
            name: "", //add discount type
            percentage: perc,
            due_date: date,
            req: "add_store_visible_discount"
        });
    };
    div.appendChild(finish);

    popUp(div);

}

function edit_permissions() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Edit manager permissions";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let user_assign = document.createElement("input");
    user_assign.placeholder="Enter manager user name";
    user_assign.className = 'text_input';
    div.appendChild(user_assign);

    let ch1 = document.createElement("input");
    ch1.type="checkbox";
    ch1.id="checkbox1";
    div.appendChild(ch1);

    alert(ch1.checked);

    popUp(div);




}

function watch_store_history(){
    let vars = instance.getUrlVars();
    let store_name = vars["s"];
    instance.edit_store_product({
        user_name: localStorage.getItem("current_username"),
        store: store_name,
        req: "watch_store_history",
    });
}

function watch_store_history_respond(products) {
    let productList = products["store_history"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';
    console.log(products);
    //DESIGN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    let d = document.createElement("div");
    d.innerHTML = "<b> productList </b>";

    popUp(div);
}

function close_store() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Are you sure you want to close "+store_name+" store?";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);


    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Yes</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){

        instance.close_store({
            user_name: localStorage.getItem("current_username"),
            store_name: store_name,
            req: "close_store"
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function remove_owner() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Remove owner from "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let user_assign = document.createElement("input");
    user_assign.placeholder="Enter owner user name";
    user_assign.className = 'text_input';
    div.appendChild(user_assign);
    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Send</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let name = user_assign.value;
        if(name === ""){
            alert("please enter the manager user name");
            return;
        }

        instance.remove_store_manager({
            user_name: localStorage.getItem("current_username"),
            store_name: store_name,
            user_assign: name,
            req: "remove_store_owner"
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function assign_owner() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Assign new owner to "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let user_assign = document.createElement("input");
    user_assign.placeholder="Enter owner user name";
    user_assign.className = 'text_input';
    div.appendChild(user_assign);
    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Send</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let name = user_assign.value;
        if(name === ""){
            alert("please enter the owner user name");
            return;
        }

        instance.assign_store_owner({
            user_name: localStorage.getItem("current_username"),
            store_name: store_name,
            user_assign: name,
            req: "assign_store_owner"
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function remove_manager(){
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "remove a manager from "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let user_assign = document.createElement("input");
    user_assign.placeholder="Enter manager user name";
    user_assign.className = 'text_input';
    div.appendChild(user_assign);
    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Send</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let name = user_assign.value;
        if(name === ""){
            alert("please enter the manager user name");
            return;
        }

        instance.remove_store_manager({
            user_name: localStorage.getItem("current_username"),
            store_name: store_name,
            user_assign: name,
            req: "remove_store_manager"
        });
    };
    div.appendChild(finish);

    popUp(div);
}



function assign_manager(){
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Assign new manager to "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let user_assign = document.createElement("input");
    user_assign.placeholder="Enter manager user name";
    user_assign.className = 'text_input';
    div.appendChild(user_assign);
    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Send</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let name = user_assign.value;
        if(name === ""){
            alert("please enter the manager user name");
            return;
        }

        instance.assign_store_manager({
            user_name: localStorage.getItem("current_username"),
            store_name: store_name,
            user_assign: name,
            req: "assign_store_manager"
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function remove_product(){
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Remove product from "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let product_name = document.createElement("input");
    product_name.placeholder="Enter product name";
    product_name.className = 'text_input';
    div.appendChild(product_name);
    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Send</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let name = product_name.value;
        if(name === ""){
            alert("please enter the product name");
            return;
        }

        instance.remove_store_product({
            user_name: localStorage.getItem("current_username"),
            store_name: store_name,
            product_name: name,
            req: "remove_store_product"
        });
    };
    div.appendChild(finish);

    popUp(div);
}

function add_product(){
    let vars = instance.getUrlVars();
    let store_name = vars["s"];

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Add new product to "+store_name+" store";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let product_name = document.createElement("input");
    product_name.placeholder="Enter product name";

    let product_price = document.createElement("input");
    product_price.placeholder="Enter the price of the product";

    let product_amount = document.createElement("input");
    product_amount.placeholder="Enter the amount of the product";


    [product_name, product_price, product_amount].forEach((d)=>{
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
        let name = product_name.value;
        let price = product_price.value;
        let amount = product_amount.value;

        if(name === ""){
            alert("please enter the product name");
            return;
        }
        if(price=== "") {
            alert("please enter the price");
            return;
        }
        if(amount=== "") {
            alert("please enter the amount");
            return;
        }

        instance.add_store_product({
            user_name: localStorage.getItem("current_username"),
            store_names: store_name,
            product_names: name,
            product_price: price,
            product_amount: amount,
            req: "add_store_product",
        });
    };
    div.appendChild(finish);

    popUp(div);
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

}

let instance = new Main();
