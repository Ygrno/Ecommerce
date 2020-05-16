
class Main{

    static getInstance(){
        return instance;
    }

    constructor(){

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

    }

    login(username, password){
        this.client.send(JSON.stringify({req:"login", username:username, password:password}));
    }

    get_stores(){
        this.client.send(JSON.stringify({req:"get_stores"}));
    }


}

let instance = new Main();
