
function logout() {
    localStorage.removeItem("current_username");
    localStorage.removeItem("guest");
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
}

let instance = new Main();
