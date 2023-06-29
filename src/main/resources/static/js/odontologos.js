window.addEventListener('load', function(){

    obtenerOdontologos();

    const buscarOdontologoForm = document.querySelector('#buscarOdontologoForm');
    const formAddOdontologo = document.querySelector('#add_odontologo');
    const id_odontologo_form = document.querySelector('#id_odontologo_form');
    const nombre = document.querySelector('#nombre_odontologo');
    const apellido = document.querySelector('#apellido');
    const matricula = document.querySelector('#matricula');
    const odontologoEncontrado = document.querySelector('#buscarporID');
    

/* -------------------------------------------------------------------------- */
/*                 Obtengo listado de pacientes [GET]                         */
/* -------------------------------------------------------------------------- */

    function  obtenerOdontologos(){

        const url = 'http://localhost:8080/odontologos';

        fetch(url)
        .then(response => {
            if(!response.ok){
                throw new Error("Error HTTP: " + response);
            }
            return response.json();
        })
        .then(data  => {
            renderizarOdontologos(data);
        })
        .catch(err =>{
            console.log(err);
        })

    };

/* -------------------------------------------------------------------------- */
/*                 Renderizo listado de pacientes                             */
/* -------------------------------------------------------------------------- */

    function renderizarOdontologos(listado){
        const listadoOdontologos = document.querySelector('#fila');

        listadoOdontologos.innerHTML = "";

        listado.forEach(odontologo => {
            listadoOdontologos.innerHTML += `
            <div class="caja">
                <h3>${odontologo.nombre} ${odontologo.apellido}</h3>
                <p>ID: ${odontologo.id}</p>
                <p>Matricula: ${odontologo.matricula}</p>
                <div class="edit-delete">
                    <button type="submit" class="delete" id="${odontologo.id}">Eliminar Paciente</button>
                    <button type="submit" class="update" id="${odontologo.id}">Modificar Paciente</button>
                </div>               
            </div> 
            `   
        eliminarOdontologos();
        renderizarOdontologoAModificar();

        });


    };


/* -------------------------------------------------------------------------- */
/*                 Buscar el paciente por ID                                  */
/* -------------------------------------------------------------------------- */

    function buscarOdontologoPorId(id){

        const url = 'http://localhost:8080/odontologos/' + id

        return fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data  => {
            console.log("ENTRO ACA, primera parte Buscar Odontologo")
            if(data.message!=undefined){
                console.log(data.message);

                odontologoEncontrado.innerHTML = "";
                odontologoEncontrado.innerHTML +=  `
                    <h3>ODONTOLOGO NO ENCONTRADO</h3>
                `
            }else{
                console.log(data)
                return data;
            }
        })
        .catch(err =>{
            console.log(err);
        })

    }


/* -------------------------------------------------------------------------- */
/*                 Obtener el paciente por ID                                  */
/* -------------------------------------------------------------------------- */

    buscarOdontologoForm.addEventListener('submit', function(e){
        console.log("lanzando buscarOdontologo form");

        e.preventDefault();
        var id = document.getElementById("id_odontologo").value;

        buscarOdontologoPorId(id)
        .then(response =>{
            if(response!=undefined){
                renderizarOdontologoPorID(response)
            }else{
                console.log("No se encontro al odontologo")
            }
        })
        .catch(e => {
            console.log(e);
        });



    });

/* -------------------------------------------------------------------------- */
/*                 Renderizo el paciente por ID                               */
/* -------------------------------------------------------------------------- */


    function renderizarOdontologoPorID(odontologo){
        console.log("lanzando renderizar odontologo")

        odontologoEncontrado.innerHTML = "";
        odontologoEncontrado.innerHTML +=  `
        <h3>${odontologo.nombre} ${odontologo.apellido}</h3>
        <p>ID: ${odontologo.id}</p>
        <p>Matricula: ${odontologo.matricula}</p>
        <div class="edit-delete">
            <button type="submit" class="delete" id="${odontologo.id}">Eliminar Paciente</button>
            <button type="submit" class="update" id="${odontologo.id}">Modificar Paciente</button>
        </div>   
        `
        eliminarOdontologos();
        renderizarOdontologoAModificar();
    }

/* -------------------------------------------------------------------------- */
/*                 Eliminar Paciente                                          */
/* -------------------------------------------------------------------------- */

    function eliminarOdontologos(){

        const elimiarOdontologosButton = document.querySelectorAll('.delete');

        elimiarOdontologosButton.forEach(button => {

            button.addEventListener('click', function(e){
                e.preventDefault();
                console.log("Lanzando eliminar odontologos")
                var id =  e.target.id;
                console.log(id);
                
                const url = 'http://localhost:8080/odontologos/' + id;

                const settings = {
                    method: "DELETE"
                }

                fetch(url, settings)
                .then(response => {
                    console.log("parte 1")
                    console.log(response)
                    if(!response.ok){
                        alert("El odontologo tiene un turno asignado")
                        return response.json(); 
                    }
                    return response.text();
                })
                .then(data  => {
                    console.log("parte 2")
                    console.log(data)
                    location.reload();
                })

            });
        });

    };


/* -------------------------------------------------------------------------- */
/*                 Tomo datos del Submit Agregar Paciente                     */
/* -------------------------------------------------------------------------- */
    formAddOdontologo.addEventListener('submit', function(e){

        e.preventDefault();
        console.log("LANZANDO EL SUBMIT DEL FORMULARIO");

        const usuario = 
        {   
            "id":id_odontologo_form.value,
            "nombre": nombre.value,
            "apellido": apellido.value,
            "matricula": matricula.value
        }

        const settingsAgregar = {
            method: "POST",
            body: JSON.stringify(usuario),
            headers:{
                'Content-Type': 'application/json'
            }
        };

        const settingsModificar = {
            method: "PUT",
            body: JSON.stringify(usuario),
            headers:{
                'Content-Type': 'application/json'
            }
        };



        if(usuario.id == ""){
            agregarOdontologo(settingsAgregar);
        }else{
            modificarOdontologo(settingsModificar)
        }

        formAddOdontologo.reset();

    });


/* -------------------------------------------------------------------------- */
/*                 Agrego el paciente                                         */
/* -------------------------------------------------------------------------- */

    function agregarOdontologo(settings){
        console.log("lanzando agregar odontologo");
        const respuesta = document.querySelector("#respuesta");

        const url = 'http://localhost:8080/odontologos/';

        fetch(url, settings)
        .then(response =>{
            //console.log(response.json());
            if(!response.ok){
                respuesta.innerHTML += `
                <p> No ha sido posible agregar, ingrese los datos correctos</p>
                `
                throw new Error("Error HTTP: " + response.status);
            }
            return response.json();
        })
        .then(data=>{
            console.log("promesa cumplida");
            console.log(data);
            location.reload();
        })
        .catch(err=>{
            console.log("promesa rechazada");
            console.log(err);
        });


    };



/* -------------------------------------------------------------------------- */
/*                 Renderizar modificar paciente                                         */
/* -------------------------------------------------------------------------- */


    function renderizarOdontologoAModificar(){

        const modificarOdontologoButtons = document.querySelectorAll('.update');

        modificarOdontologoButtons.forEach(button => {

            button.addEventListener('click', function(e){
                var id =  e.target.id;
                console.log('LANZANDO BUTTON MODIFICAR');
                buscarOdontologoPorId(id)
                .then(data =>{
                    const odontologoAModificar = 
                    {   
                        "id": data.id,
                        "nombre": data.nombre,
                        "apellido": data.apellido,
                        "matricula": data.matricula,
                    };

                    id_odontologo_form.value = odontologoAModificar.id;
                    nombre.value = odontologoAModificar.nombre;
                    apellido.value = odontologoAModificar.apellido;
                    matricula.value = odontologoAModificar.matricula;
                })
                .catch(e => {
                    console.log(e);
                });

                })             
            });

    };



/* -------------------------------------------------------------------------- */
/*                  Modificar paciente                                         */
/* -------------------------------------------------------------------------- */


    function modificarOdontologo(settings){
        console.log("lanzando modificar odontologo");

        const url = 'http://localhost:8080/odontologos/';

        fetch(url, settings)
        .then(response =>{
            if(!response.ok){
                throw new Error("Error HTTP: " + response.status);
            }
            console.log(response);
            return response.json;
        })
        .then(data=>{
            console.log("promesa cumplida");
            console.log(data);
            location.reload();
        })
        .catch(err=>{
            console.log("promesarechazada");
            console.log(err);
        });

    };













});
