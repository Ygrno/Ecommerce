
class HTMLList {

    constructor(parent){
        this.parent = parent;
        this.elements = [];
    }

    addElement(element){
        this.elements.push(element);
    }

    render(divCreator){

        let that = this;

        this.elements.forEach((el)=> {
            let div = divCreator(el);
            that.parent.appendChild(div);
        });

    }

    rerender(divCreator){
        this.parent.innerHTML = '';
        this.render(divCreator);
    }




}