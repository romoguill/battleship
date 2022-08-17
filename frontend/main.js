const onClick = function() {
    alert("seleccione en que direccion orienta su barco "+this.id)};
    const onClickShip = function() {
        alert("seleccione donde ubica su barco "+this.id)};
    ships=[5,4,3,3,2]
    let prueba2;
    let prueba;
    for(j=0;j<ships.length;j++){
        prueba=document.createElement("div")
        prueba.classList.add("ship")
        prueba.setAttribute("id","ship"+j)
        document.getElementById("commandControl").appendChild(prueba);
        for (i=0;i<ships[j];i++){
            prueba2=document.createElement("div")
            document.getElementById("ship"+j).appendChild(prueba2);
            if(i==0){prueba2.classList.add("openRoundBox")}
            if(i==ships[j]-1){prueba2.classList.add("closeRoundBox")}
            if (i>0&&i<ships[j]-1){prueba2.classList.add("box")}
            prueba2.classList.add("idea")
            prueba2.innerHTML=ships[j]
            prueba2.setAttribute('id','id'+j+i)
            prueba.onclick=onClickShip;
        }
    }
    



    
for(i=0;i<100;i++){
    const para = document.createElement("div");
    para.classList.add("box")
    para.classList.add("clase"+i)
    para.innerHTML =i;
    para.onclick=onClick
 
    para.setAttribute('id','id'+i)
   
   
   
    
    
    
    // Append to anothner element:
    document.getElementById("wrapper").appendChild(para);}