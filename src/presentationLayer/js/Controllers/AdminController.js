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
        parent.appendChild(name);
        let finish = document.createElement("div");
        finish.className = 'Green_beautiful_div';
        finish.innerHTML = '<b>Finish</b>';
        finish.style.fontSize = '50px';
        finish.style.width = '200px';
        // finish.style.marginTop = '3%';
        finish.onclick = function(){
            //////TODO : add functionality
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
            instance.send_process_details({
                store_name: store,
                req: "view_history_store"
            });
        };
        parent.appendChild(finish);
    }

    view_customer_history(parent){
        this.clear(parent);
        let entroDiv = document.createElement("div");
        entroDiv.innerHTML = "Insert The User Name You Wish To View His History: ";
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
            let user = name.value;
            if(user === ""){
                alert("please enter the user name");
                return;
            }
            instance.send_process_details({
                user_name: user,
                req: "view_customer_history"
            });
        };
        parent.appendChild(finish);
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

}



let _AdminInstance = new Admin();