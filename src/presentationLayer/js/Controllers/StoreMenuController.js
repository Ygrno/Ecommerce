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

function add_visible_discount() {
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

    let discountName = document.createElement("input");
    discountName.placeholder="Enter discount name";
    discountName.className = 'text_input';
    div.appendChild(discountName);

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
        let discount_name = discountName.value;
        if(discount_name === "") {
            alert("please enter the discount name");
            //add discount type
        }
        instance.add_store_visible_discount({
            user_name: localStorage.getItem("current_username"),
            store: store_name,
            product: product,
            name: discount_name, //add discount type
            percentage: perc,
            due_date: date,
            req: "add_store_visible_discount"
        });
    };
    div.appendChild(finish);

    popUp(div);

}

function add_conditioned_discount() {
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

    let discountName = document.createElement("input");
    discountName.placeholder="Enter discount name";
    discountName.className = 'text_input';
    div.appendChild(discountName);

    let percentage = document.createElement("input");
    percentage.placeholder="Enter discount percentage (from 0 to 1)";
    percentage.className = 'text_input';
    div.appendChild(percentage);

    let percentageDate = document.createElement("input");
    percentageDate.placeholder="Enter discount due date (ddmmyyyy without /)";
    percentageDate.className = 'text_input';
    div.appendChild(percentageDate);

    let requiredAmount = document.createElement("input");
    requiredAmount.placeholder="Enter Required Product Amount To Get The Discount";
    requiredAmount.className = 'text_input';
    div.appendChild(requiredAmount);

    let requiredSum = document.createElement("input");
    requiredSum.placeholder="Enter Required Shopping Bag Sum To Get The Discount";
    requiredSum.className = 'text_input';
    div.appendChild(requiredSum);

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
        let discount_name = discountName.value;
        if(discount_name === "") {
            alert("please enter the discount name");
        }
        let amount = requiredAmount.value;
        if(amount === "") {
            alert("please enter the required amount");
        }
        let sum = requiredSum.value;
        if(sum === "") {
            alert("please enter the required sum");
        }

        instance.add_store_conditioned_discount({
            user_name: localStorage.getItem("current_username"),
            store: store_name,
            product: product,
            name: discount_name, //add discount type
            percentage: perc,
            due_date: date,
            amount: amount,
            sum: sum,
            req: "add_store_conditioned_discount"
        });
    };
    div.appendChild(finish);

    popUp(div);

}

function add_complex_discount() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Add discount";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);


    let discountName = document.createElement("input");
    discountName.placeholder="Enter discount name";
    discountName.className = 'text_input';
    div.appendChild(discountName);

    let discounts = document.createElement("input");
    discounts.placeholder="Enter discounts names (with space between each name)";
    discounts.className = 'text_input';
    div.appendChild(discounts);

    let discountType = document.createElement("input");
    discountType.placeholder="Enter discount type (and, or, one)";
    discountType.className = 'text_input';
    div.appendChild(discountType);

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

        let date = percentageDate.value;
        if(date === ""){
            alert("please enter the date");
            return;
        }
        let discount_name = discountName.value;
        if(discount_name === "") {
            alert("please enter the discount name");
        }
        let discountsNames = discounts.value;
        if(discountsNames === "") {
            alert("please enter the discounts names amount");
        }
        let type = discountType.value;
        if(type === "") {
            alert("please enter the discount type");
        }

        instance.add_store_conditioned_discount({
            user_name: localStorage.getItem("current_username"),
            store: store_name,
            name: discount_name, //add discount type
            discounts: discountsNames,
            due_date: date,
            type: type,
            req: "add_store_complex_discount"
        });
    };
    div.appendChild(finish);

    popUp(div);

}

function delete_discount() {
    let vars = instance.getUrlVars();
    let store_name = vars["s"];
    let div = document.createElement("div");
    div.style.overflow = 'auto';

    let introDiv = document.createElement("div");
    introDiv.innerHTML = "Delete discount";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);


    let discountName = document.createElement("input");
    discountName.placeholder="Enter discount name";
    discountName.className = 'text_input';
    div.appendChild(discountName);


    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Finish</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';
    finish.onclick = function(){
        let discount_name = discountName.value;
        if(discount_name === "") {
            alert("please enter the discount name");
        }

        instance.delete_discount({
            user_name: localStorage.getItem("current_username"),
            store: store_name,
            name: discount_name, //add discount type
            req: "delete_discount"
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
    introDiv.innerHTML = "Edit "+store_name+" management permissions";
    introDiv.className = 'total_price_div';
    div.appendChild(introDiv);

    let user_assign = document.createElement("input");
    user_assign.placeholder="Enter manager user name";
    user_assign.className = 'text_input';
    div.appendChild(user_assign);

    user_assign.onkeydown = function(){
        if(event.key === 'Enter') {
            instance.get_user_permissions(store_name, user_assign.value);
        }
    };

    popUp(div);
}

function on_permissions_recieved(permissions){
    console.log(permissions);
    clearPopUp();



    let permissions_status = [
        {p:"VIEW_AND_RESPOND_TO_USERS", assigned:false},
        {p:"VIEW_STORE_HISTORY", assigned:false},
        {p:"ADD_PRODUCT", assigned:false},
        {p:"EDIT_PRODUCT", assigned:false},
        {p:"REMOVE_PRODUCT", assigned:false},
        {p:"EDIT_STORE_POLICY", assigned:false},
        {p:"ASSIGN_STORE_MANAGER", assigned:false},
        {p:"REMOVE_STORE_MANAGER", assigned:false},
        {p:"ADD_DISCOUNT", assigned:false},
        {p:"ADD_BUY_POLICY", assigned:false}
    ];

    const assignPermission = (l, p, a)=>{
        l.forEach((el)=>{
            if(el.p === p){
                el.assigned = a;
            }
        });
    };

    let user_permission_list = permissions["permissions"];

    user_permission_list.forEach((perm)=>
        assignPermission(permissions_status, perm.p, true)
    );

    let parent = document.createElement("div");
    parent.style.overflow = "auto";

    let cb_list = new HTMLList(parent);

    let cb_creator = (ps)=>{
        let p_name = ps.p;
        let assigned = ps.assigned;

        let label = document.createElement("label");
        label.className = "cb_container";
        label.innerHTML = p_name;

        let input = document.createElement("input");
        input.type = "checkbox";
        input.checked = assigned;

        input.onclick = function (){
            ps.assigned = input.checked;
        };

        let span = document.createElement("span");
        span.className = "checkmark";

        label.appendChild(input);
        label.appendChild(span);

        return label;
    };

    permissions_status.forEach((ps)=>{
        cb_list.addElement(ps);
    });

    cb_list.render(cb_creator);

    let finish = document.createElement("div");
    finish.className = 'Green_beautiful_div';
    finish.innerHTML = '<b>Edit</b>';
    finish.style.fontSize = '30px';
    finish.style.width = '100px';
    finish.style.marginTop = '3%';

    finish.onclick = function (){
        let pms = permissions_status.filter((p)=>p.assigned).map((p)=>p.p);
        console.log(pms);
        instance.edit_manager_permissions(permissions.store, permissions.username, pms);

    };

    parent.appendChild(finish);
    popUp(parent);
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
    alert(JSON.stringify(products));
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
    alert(JSON.stringify(response));
}

function send_query_to_store_response(response) {
    alert(JSON.stringify(response));
}

function visible_discount_response(response) {
    alert(JSON.stringify(response));
}

function conditioned_discount_response(response) {
    alert(JSON.stringify(response));
}

function complex_discount_response(response) {
    alert(JSON.stringify(response));
}

function delete_discount_response(response) {
    alert(JSON.stringify(response));
}