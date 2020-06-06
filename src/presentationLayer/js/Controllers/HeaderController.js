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
