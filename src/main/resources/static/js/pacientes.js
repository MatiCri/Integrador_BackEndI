window.addEventListener('load', function(){

    obtenerPacientes();

    const buscarPacienteForm = document.querySelector('#buscarPacienteForm');
    const formAddEstudiiante = document.querySelector('#add_estudiante');
    const id_paciente_form = document.querySelector('#id_paciente_form');
    const nombre = document.querySelector('#nombre_paciente');
    const apellido = document.querySelector('#apellido');
    const dni = document.querySelector('#dni');
    const fecha_ingreso = document.querySelector('#fecha_ingreso');
    const calle_domicilio = document.querySelector('#domicilio_calle');
    const nro_domicilio = document.querySelector('#nro_domicilio');
    const localidad_domiclio = document.querySelector('#localidad_domicilio');
    const prov_domicilio = document.querySelector('#provincia_domicilio');
    const pacienteEncontrado = document.querySelector('#buscarporID');
    

/* -------------------------------------------------------------------------- */
/*                 Obtengo listado de pacientes [GET]                         */
/* -------------------------------------------------------------------------- */

    function  obtenerPacientes(){

        const url = 'http://localhost:8080/pacientes';

        fetch(url)
        .then(response => {
            if(!response.ok){
                throw new Error("Error HTTP: " + response);
            }
            return response.json();
        })
        .then(data  => {
            renderizarPacientes(data);
        })
        .catch(err =>{
            console.log(err);
        })

    };

/* -------------------------------------------------------------------------- */
/*                 Renderizo listado de pacientes                             */
/* -------------------------------------------------------------------------- */

    function renderizarPacientes(listado){
        const listadoPacientes = document.querySelector('#fila');

        listadoPacientes.innerHTML = "";

        listado.forEach(paciente => {
            listadoPacientes.innerHTML += `
            <div class="caja">
                <h3>${paciente.nombre} ${paciente.apellido}</h3>
                <p>ID: ${paciente.id}</p>
                <p>DNI: ${paciente.dni}</p>
                <p>Fecha Ingreso: ${paciente.fechaIngreso}</p>
                <p>Domicilio: ${paciente.domicilioDTO.calle}</p> 
                <div class="edit-delete">
                    <button type="submit" class="delete" id="${paciente.id}">Eliminar Paciente</button>
                    <button type="submit" class="update" id="${paciente.id}">Modificar Paciente</button>
                </div>               
            </div> 
            `   
        eliminarPacientes();
        renderizarPacienteAModificar();

        });


    };


/* -------------------------------------------------------------------------- */
/*                 Buscar el paciente por ID                                  */
/* -------------------------------------------------------------------------- */

    function buscarPacientePorID(id){

        const url = 'http://localhost:8080/pacientes/' + id

        return fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data  => {
            console.log("ENTRO ACA, primera parte Buscar Paciente")
            if(data.message!=undefined){
                console.log(data.message);

                pacienteEncontrado.innerHTML = "";
                pacienteEncontrado.innerHTML +=  `
                    <h3>PACIENTE NO ENCONTRADO</h3>
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

    buscarPacienteForm.addEventListener('submit', function(e){
        console.log("lanzando buscarpaciente form");

        e.preventDefault();
        var id = document.getElementById("id_paciente").value;

        buscarPacientePorID(id)
        .then(response =>{
            if(response!=undefined){
                renderizarPacientePorID(response)
            }else{
                console.log("No se encontro al paciente")
            }
        })
        .catch(e => {
            console.log(e);
        });



    });

/* -------------------------------------------------------------------------- */
/*                 Renderizo el paciente por ID                               */
/* -------------------------------------------------------------------------- */


    function renderizarPacientePorID(paciente){
        console.log("lanzando renderizar paciente")

        

        pacienteEncontrado.innerHTML = "";
        pacienteEncontrado.innerHTML +=  `
            <h3>${paciente.nombre} ${paciente.apellido}</h3>
            <p>ID: ${paciente.id}</p>
            <p>DNI: ${paciente.dni}</p>
            <p>Fecha Ingreso: ${paciente.fechaIngreso}</p>
            <p>Domicilio: ${paciente.domicilioDTO.calle}</p> 
            <div class="edit-delete">
                <button type="submit" class="delete" id="${paciente.id}">Eliminar Paciente</button>
                <button type="submit" class="update" id="${paciente.id}">Modificar Paciente</button>
            </div>   
        `
        eliminarPacientes();
        renderizarPacienteAModificar();
    }

/* -------------------------------------------------------------------------- */
/*                 Eliminar Paciente                                          */
/* -------------------------------------------------------------------------- */

    function eliminarPacientes(){

        const eliminarPacienteButtons = document.querySelectorAll('.delete');

        eliminarPacienteButtons.forEach(button => {

            button.addEventListener('click', function(e){
                e.preventDefault();

                var id =  e.target.id;
                console.log(id);
                
                const url = 'http://localhost:8080/pacientes/' + id;

                const settings = {
                    method: "DELETE"
                }

                fetch(url, settings)
                .then(response => {
                    return response.text();
                })
                .then(data  => {
                    console.log(data);
                    location.reload();
                })

            });
        });

    };


/* -------------------------------------------------------------------------- */
/*                 Tomo datos del Submit Agregar Paciente                     */
/* -------------------------------------------------------------------------- */
    formAddEstudiiante.addEventListener('submit', function(e){

        e.preventDefault();
        console.log("LANZANDO EL SUBMIT DEL FORMULARIO");

        const usuario = 
        {   
            "id":id_paciente_form.value,
            "nombre": nombre.value,
            "apellido": apellido.value,
            "dni": dni.value,
            "fechaIngreso": fecha_ingreso.value,
            "domicilio":{
                "calle": calle_domicilio.value,
                "numero": nro_domicilio.value,
                "localidad": localidad_domiclio.value,
                "provincia": prov_domicilio.value
            }
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
            agregarPaciente(settingsAgregar);
        }else{
            modificarPaciente(settingsModificar)
        }

        formAddEstudiiante.reset();

    });


/* -------------------------------------------------------------------------- */
/*                 Agrego el paciente                                         */
/* -------------------------------------------------------------------------- */

    function agregarPaciente(settings){
        console.log("lanzando agregar paciente");
        const respuesta = document.querySelector("#respuesta");

        const url = 'http://localhost:8080/pacientes/';

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


    function renderizarPacienteAModificar(){

        const modificarPacientesButttons = document.querySelectorAll('.update');

        modificarPacientesButttons.forEach(button => {

            button.addEventListener('click', function(e){
                var id =  e.target.id;
                console.log('LANZANDO BUTTON MODIFICAR');
                buscarPacientePorID(id)
                .then(data =>{
                    const pacienteAModificar = 
                    {   
                        "id": data.id,
                        "nombre": data.nombre,
                        "apellido": data.apellido,
                        "dni": data.dni,
                        "fechaIngreso": data.fechaIngreso,
                        "domicilio":{
                            "calle": data.domicilioDTO.calle,
                            "numero": data.domicilioDTO.numero,
                            "localidad": data.domicilioDTO.localidad,
                            "provincia": data.domicilioDTO.provincia
                        }
                    };

                    id_paciente_form.value = pacienteAModificar.id;
                    nombre.value = pacienteAModificar.nombre;
                    apellido.value = pacienteAModificar.apellido;
                    dni.value = pacienteAModificar.dni;
                    fecha_ingreso.value = pacienteAModificar.fechaIngreso;
                    calle_domicilio.value = pacienteAModificar.domicilio.calle;
                    nro_domicilio.value = pacienteAModificar.domicilio.numero;
                    localidad_domiclio.value = pacienteAModificar.domicilio.localidad;
                    prov_domicilio.value = pacienteAModificar.domicilio.provincia;

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


    function modificarPaciente(settings){
        console.log("lanzando modificar paciente");

        const url = 'http://localhost:8080/pacientes/';

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
