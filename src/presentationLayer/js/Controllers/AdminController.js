class Admin{

    constructor(){

    }

    removeSubscriber(parent){
        this.clear(parent);

        let input = document.createElement("input");
        input.type = 'text';
        input.placeholder = "AAA";
        parent.appendChild(input);

    }

    clear(parent){
        parent.innerHTML = '';
    }

}



let _AdminInstance = new Admin();