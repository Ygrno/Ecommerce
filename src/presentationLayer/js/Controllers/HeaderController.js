function get_notifications(response){
    if(isGuest()){
        alert("You should sign in first");
        return;
    }
    alert(JSON.stringify(response));
    let notifications = response["notifications"];
    if (notifications.length === 0) return;
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let notificationHTMLList = new HTMLList(div);

    const cellBuilder = (product)=>{
        //DESIGN !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        let d = document.createElement("div");
        d.innerHTML = "<b>"+JSON.stringify(product)+"</b>";
        return d;
    };

    notifications.forEach((p)=> notificationHTMLList.addElement(p));
    notificationHTMLList.render(cellBuilder);
    div.appendChild(BuyButton);
    popUp(div);
}

function openStoreResponse(response) {
    let bool = response["success"];
    if(bool === true)
        alert("process completed successfully");
    else
        alert("process uncompleted something is wrong!");
}



function buyThings(products){
    clearPopUp();

    let div = document.createElement("div");
    div.style.overflow = 'auto';
    let productList = products["productsInCart"];

    let cost = products["price"];

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
    let buyer_id = document.createElement("input");
    buyer_id.placeholder="ID";

    let address = document.createElement("input");
    address.placeholder="address";

    let city = document.createElement("input");
    city.placeholder="city";

    let country = document.createElement("input");
    country.placeholder="country";

    let zip = document.createElement("input");
    zip.placeholder="zip";


    [name, credit_card_number, expire_date, cvv, buyer_id,address,city,country,zip].forEach((d)=>{
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
        let buyer_idValue = buyer_id.value;
        let adressValue=address.value;
        let cityValue = city.value;
        let countryValue= country.value;
        let zipValue = zip.value;
        if(buyerName === ""){
            alert("please enter the name");
            return;
        }
        if(ccNumber=== "" || ccNumber.length !==16) {
            alert("please enter valid credit card number");
            return;
        }
        if( expire_date_value=== "" || [0,expire_date_value.length-1,-1].indexOf(expire_date_value.indexOf("/"))!==-1) {
            alert("please enter valid expire date");
            return;
        }
        if(cvvValue=== "" || cvvValue.length!==3) {
            alert("please enter valid cvv");
            return;
        }
        if(buyer_idValue=== "" || buyer_idValue.length!==9) {
            alert("please enter valid Buyer id");
            return;
        }
        if(adressValue=== "") {
            alert("please enter the address");
            return;
        }
        if(cityValue=== "") {
            alert("please enter the city");
            return;
        }
        if(countryValue=== "") {
            alert("please enter the country");
            return;
        }
        if(zipValue=== "") {
            alert("please enter the zip code");
            return;
        }
        instance.send_process_details({
            buyer_name: buyerName,
            creditCardNumber: ccNumber,
            expireDate: expire_date_value,
            cvv: cvvValue,
            buyer_id:buyer_idValue,
            address:adressValue,
            city:cityValue,
            country:countryValue,
            zip:zipValue
        });
    };
    div.appendChild(finish);

    popUp(div);
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

function viewPurchaseHistory(products){
    if(isGuest()){
        alert("You should sign in first");
        return;
    }


    let productList = products["products_in_history"];
    if (productList.length === 0) return;
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let purchaseDiv = document.createElement("div");
    purchaseDiv.innerHTML = "Purchases : "+productList;
    purchaseDiv.className = 'total_price_div';
    div.appendChild(purchaseDiv);

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

function remove_product_cart(){

    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Remove Product From Cart";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let product_name = document.createElement("input");
    product_name.placeholder="Enter Product Name";
    product_name.className = 'text_input';
    div.appendChild(product_name);

    let store_name = document.createElement("input");
    store_name.placeholder="Enter Store Name";
    store_name.className = 'text_input';
    div.appendChild(store_name);


    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Finish</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let product = product_name.value;
        if(product === ""){
            alert("please enter the product name");
            return;
        }
        let storeName = store_name.value;
        if(storeName === ""){
            alert("please enter the store name");
            return;
        }
        instance.remove_product_cart({
            product: product,
            store:storeName
        });
    };

    div.appendChild(finish);
    popUp(div);
}

function remove_cart_product_response(response) {
    let bool = response["success"];
    if(bool === true)
        alert("process completed successfully");
    else
        alert("process uncompleted something is wrong!");
}