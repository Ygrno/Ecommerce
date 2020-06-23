
function AdminMsgCallBack(req, response){
    if(req === "remove_subscriber"){
        AdminRemoveSubscriber(response);
    }else if(req === "view_history_store"){
        AdminViewStoreHistory(response, "view_store_history_result");
    }else if(req === "view_history_costumer"){
        AdminViewCustomerHistory(response, "view_customer_history_result");
    }
}



class Admin{

    constructor(){

    }

    removeSubscriber(parent){
        this.clear(parent);
        let entroDiv = document.createElement("div");
        entroDiv.innerHTML = "Insert The Subscriber User Name you Want To Remove: ";
        entroDiv.className = 'entro_div';
        parent.appendChild(entroDiv);
        let name = document.createElement("input");
        name.placeholder="Name";
        name.className= 'tex_input';
        name.id = "remove_username_input";
        parent.appendChild(name);
        let finish = document.createElement("div");
        finish.className = 'Green_beautiful_div';
        finish.innerHTML = '<b>Finish</b>';
        finish.style.fontSize = '50px';
        finish.style.width = '200px';
        // finish.style.marginTop = '3%';
        finish.onclick = function(){
            //////TODO : send to 'remove_subscriber' request to server
            instance.remove_subscriber(name.value);
        };
        parent.appendChild(finish);
    }

    view_history_store(parent){
        this.clear(parent);
        this.clear(parent);
        let entroDiv = document.createElement("div");
        entroDiv.innerHTML = "Insert The Store Name You Wish To View Its History: ";
        entroDiv.className = 'entro_div';
        parent.appendChild(entroDiv);
        let name = document.createElement("input");
        name.placeholder="Store Name";
        name.className= 'tex_input';
        parent.appendChild(name);
        let finish = document.createElement("div");
        finish.className = 'Green_beautiful_div';
        finish.innerHTML = '<b>View</b>';
        finish.style.fontSize = '50px';
        finish.style.width = '200px';
        // finish.style.marginTop = '3%';
        finish.onclick = function(){
            let store = name.value;
            if(store === ""){
                alert("please enter the store name");
                return;
            }
            instance.sendToServer({
                store_name: store,
                req: "view_history_store"
            });
        };
        parent.appendChild(finish);
        let result_div = document.createElement("div");
        result_div.id = "view_store_history_result";
        result_div.className = "view_history_result";
        parent.appendChild(result_div);
    }

    view_customer_history(parent){
        this.clear(parent);
        let entroDiv = document.createElement("div");
        entroDiv.innerHTML = "Insert The User Name You Wish To View His History: ";
        entroDiv.className = 'entro_div';
        parent.appendChild(entroDiv);
        let name = document.createElement("input");
        name.placeholder="Subscriber username";
        name.className= 'tex_input';
        parent.appendChild(name);
        let finish = document.createElement("div");
        finish.className = 'Green_beautiful_div';
        finish.innerHTML = '<b>View</b>';
        finish.style.fontSize = '50px';
        finish.style.width = '200px';
        // finish.style.marginTop = '3%';
        finish.onclick = function(){
            let user = name.value;
            if(user === ""){
                alert("please enter the user name");
                return;
            }
            instance.sendToServer({
                user_name: user,
                req: "view_history_costumer"
            });
        };
        parent.appendChild(finish);

        let result_div = document.createElement("div");
        result_div.id = "view_customer_history_result";
        result_div.className = "view_history_result";
        parent.appendChild(result_div);

    }

    today_revenue(parent){
        this.clear(parent);
        instance.today_revenue();
    }

    data_revenue(parent){
        this.clear(parent);
        let entroDiv = document.createElement("div");
        entroDiv.innerHTML = "Insert The Date You Wish To View Its Revenue: ";
        entroDiv.className = 'entro_div';
        parent.appendChild(entroDiv);
        let date = document.createElement("input");
        date.placeholder="date (format : ddmmyyyy)";
        date.className= 'tex_input';
        parent.appendChild(name);
        let finish = document.createElement("div");
        finish.className = 'Green_beautiful_div';
        finish.innerHTML = '<b>View</b>';
        finish.style.fontSize = '50px';
        finish.style.width = '200px';
        // finish.style.marginTop = '3%';
        finish.onclick = function(){
            let date = name.value;
            if(date === ""){
                alert("please enter the date");
                return;
            }
            instance.date_revenue({
                date: date,
                req: "date_revenue"
            });
        };
        parent.appendChild(finish);
    }


    clear(parent){
        parent.innerHTML = '';
    }

} let _AdminInstance = new Admin();



function AdminRemoveSubscriber(response){
    if(response.success){
        alert("Removed successfully !");
    }else{
        alert("Subscriber is not exist or already removed");
    }
    let remove_username_input = document.getElementById("remove_username_input");
    if( remove_username_input ) remove_username_input.value = "";
}

function AdminViewStoreHistory(response, result_div) {
    let parent = document.getElementById(result_div);

    let history = response.history;

    history.forEach((purchaseProcess)=>{
        let username = purchaseProcess.username;
        let price = purchaseProcess.price;
        let products = purchaseProcess.products;

        let purchase_div = document.createElement("div");
        purchase_div.className = "purchase_div";

        let name_span = document.createElement("span");
        name_span.innerHTML = "<b>Purchased By :</b>" + username;
        purchase_div.appendChild(name_span);

        let price_span = document.createElement("span");
        price_span.innerHTML = "<b>Price :</b>" + price;
        purchase_div.appendChild(price_span);

        let ul = document.createElement("ul");

        products.forEach((product)=>{
           let li = document.createElement("li");
           li.innerHTML = product.name;
           ul.appendChild(li);
        });

        purchase_div.appendChild(ul);
        parent.appendChild(purchase_div);
    });

}

function AdminViewCustomerHistory(response, result_div) {
    let parent = document.getElementById(result_div);

    let history = response.history;

    history.forEach((purchaseProcess)=>{
        let store = purchaseProcess.store_name;
        let price = purchaseProcess.price;
        let products = purchaseProcess.products;

        let purchase_div = document.createElement("div");
        purchase_div.className = "purchase_div";

        let name_span = document.createElement("span");
        name_span.innerHTML = "<b>Purchased At :</b>" + store;
        purchase_div.appendChild(name_span);

        let price_span = document.createElement("span");
        price_span.innerHTML = "<b>Price :</b>" + price;
        purchase_div.appendChild(price_span);

        let ul = document.createElement("ul");

        products.forEach((product)=>{
            let li = document.createElement("li");
            li.innerHTML = product.name;
            ul.appendChild(li);
        });

        purchase_div.appendChild(ul);
        parent.appendChild(purchase_div);
    });

}












