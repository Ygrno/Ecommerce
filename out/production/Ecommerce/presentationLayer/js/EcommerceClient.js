

class EcommerceClient{

    constructor(ip, port) {
        this.port = port;
        this.ip = ip;

        this.messageListeners = [];
    }

    start(){
        this.websocket = new WebSocket("ws://" + this.ip + ":" + this.port + "/");
        //init message listener
        this._initListener();
        this._handleOpen();

    }

    setOpenCallBack(onopen){
        this.onopen = onopen;
    }

    _handleOpen() {
        let that = this;
        this.websocket.onopen = function (evt) {
            that.onopen();
        };
    }

    _initListener(){
        let that = this;
        this.websocket.onmessage = function (response) {
            let msg = response.data;
            that.messageListeners.forEach((observer)=>observer(msg));
        }
    }

    send(msg) {
        if (this.websocket === undefined) {
            console.log("> Can't send a message");
            console.log("> Websocket is not initialized yet")
            console.log("> run start() to initialize")
        } else {
            this.websocket.send(msg);
        }
    }

    /**
     * add a server message listener
     * {@param observer} should be a callback of type (msg:string)=>void
     * **/
    addMessageListener(observer){
        this.messageListeners.push(observer);
    }


}