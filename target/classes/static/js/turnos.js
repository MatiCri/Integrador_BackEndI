window.addEventListener('load', function(){

    obtenerTurnos();

    const buscarTurnoForm = document.querySelector('#buscarTurnoForm');
    const formAddTurno = document.querySelector('#add_turno');
    const id_turno_form = document.querySelector('#id_turno_form');
    const idPacienteTurno = document.querySelector('#id_paciente_turno');
    const idOdontologoTurno = document.querySelector('#id_odontologo_turno');
    const fechaHora = document.querySelector('#fecha_hora_turno');
    const turnoEncontrado = document.querySelector('#buscarporID');
    

/* -------------------------------------------------------------------------- */
/*                 Obtengo listado de turnos [GET]                         */
/* -------------------------------------------------------------------------- */

    function  obtenerTurnos(){

        const url = 'http://localhost:8080/turnos';

        fetch(url)
        .then(response => {
            if(!response.ok){
                throw new Error("Error HTTP: " + response);
            }
            return response.json();
        })
        .then(data  => {
            renderizarTurnos(data);
        })
        .catch(err =>{
            console.log(err);
        })

    };

/* -------------------------------------------------------------------------- */
/*                 Renderizo listado de turnos                             */
/* -------------------------------------------------------------------------- */

    function renderizarTurnos(listado){
        const listadoTurnos = document.querySelector('#fila');

        listadoTurnos.innerHTML = "";

        listado.forEach(turno => {
            listadoTurnos.innerHTML += `
            <div class="caja">
                <h3>${turno.paciente} y ${turno.odontologo} </h3>
                <p>ID Turno: ${turno.id}</p>
                <p>Fecha: ${turno.fechaTurno}</p>
                <div class="edit-delete">
                    <button type="submit" class="delete" id="${turno.id}">Eliminar Turno</button>
                    <button type="submit" class="update" id="${turno.id}">Modificar Turno</button>
                </div>               
            </div> 
            `   
        eliminarTurnos();
        renderizarTurnoAModificar();

        });


    };


/* -------------------------------------------------------------------------- */
/*                 Buscar el turno por ID                                  */
/* -------------------------------------------------------------------------- */

    function buscarTurnoPorID(id){

        const url = 'http://localhost:8080/turnos/' + id

        return fetch(url)
        .then(response => {
            return response.json();
        })
        .then(data  => {
            console.log("ENTRO ACA, primera parte Buscar Turno")
            if(data.message!=undefined){
                console.log(data.message);

                turnoEncontrado.innerHTML = "";
                turnoEncontrado.innerHTML +=  `
                    <h3>TURNO NO ENCONTRADO</h3>
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
/*                 Obtener el turno por ID                                  */
/* -------------------------------------------------------------------------- */

    buscarTurnoForm.addEventListener('submit', function(e){
        console.log("lanzando buscarturno form");

        e.preventDefault();
        var id = document.getElementById("id_turno").value;

        buscarTurnoPorID(id)
        .then(response =>{
            if(response!=undefined){
                renderizarTurnoPorID(response)
            }else{
                console.log("No se encontro al turno")
            }
        })
        .catch(e => {
            console.log(e);
        });

    });

/* -------------------------------------------------------------------------- */
/*                 Renderizo el turno por ID                               */
/* -------------------------------------------------------------------------- */


    function renderizarTurnoPorID(turno){
        console.log("lanzando renderizar turno")


        turnoEncontrado.innerHTML = "";
        turnoEncontrado.innerHTML +=  `
            <h3>${turno.paciente} y ${turno.odontologo} </h3>
            <p>ID Turno: ${turno.id}</p>
            <p>Fecha: ${turno.fechaTurno}</p>
            <div class="edit-delete">
                <button type="submit" class="delete" id="${turno.id}">Eliminar Turno</button>
                <button type="submit" class="update" id="${turno.id}">Modificar Turno</button>
            </div>               
        `
        eliminarTurnos();
        renderizarTurnoAModificar();
    }

/* -------------------------------------------------------------------------- */
/*                 Eliminar Turno                                          */
/* -------------------------------------------------------------------------- */

    function eliminarTurnos(){

        const eliminarTurnoButtons = document.querySelectorAll('.delete');

        eliminarTurnoButtons.forEach(button => {

            button.addEventListener('click', function(e){
                e.preventDefault();

                var id =  e.target.id;
                console.log(id);
                
                const url = 'http://localhost:8080/turnos/' + id;

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
/*                 Tomo datos del Submit Agregar Turno                     */
/* -------------------------------------------------------------------------- */
    formAddTurno.addEventListener('submit', function(e){

        e.preventDefault();
        console.log("LANZANDO EL SUBMIT DEL FORMULARIO");

        const usuario = 
        {   
            "id": id_turno_form.value,
            "paciente":{
                "id": idPacienteTurno.value
            },
            "odontologo":{
                "id": idOdontologoTurno.value
            },
            "fechaTurno": fechaHora.value
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
            agregarTurno(settingsAgregar);
        }else{
            modificarTurno(settingsModificar)
        }

        formAddTurno.reset();

    });


/* -------------------------------------------------------------------------- */
/*                 Agrego el turno                                         */
/* -------------------------------------------------------------------------- */

    function agregarTurno(settings){
        console.log("lanzando agregar turno");
        const respuesta = document.querySelector("#respuesta");

        const url = 'http://localhost:8080/turnos/';

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
/*                 Renderizar modificar turno                                         */
/* -------------------------------------------------------------------------- */


    function renderizarTurnoAModificar(){

        const modificarTurnosButttons = document.querySelectorAll('.update');

        modificarTurnosButttons.forEach(button => {

            button.addEventListener('click', function(e){
                var id =  e.target.id;
                console.log('LANZANDO BUTTON MODIFICAR');
                buscarTurnoPorID(id)
                .then(data =>{
                    const turnoAModificar = 
                    {   
                        "id": data.id,
                        "paciente":{
                            "id": data.paciente
                        },
                        "odontologo":{
                            "id": data.odontologo
                        },
                        "fechaTurno": fechaHora.value
                    };

                    console.log(turnoAModificar)

                    id_turno_form.value = turnoAModificar.id;
                    idPacienteTurno.value = turnoAModificar.paciente.id;
                    idOdontologoTurno.value = turnoAModificar.odontologo.id;
                    fechaHora.value = turnoAModificar.fechaTurno;

                })
                .catch(e => {
                    console.log(e);
                });

                })             
            });

    };



/* -------------------------------------------------------------------------- */
/*                  Modificar turno                                         */
/* -------------------------------------------------------------------------- */


    function modificarTurno(settings){
        console.log("lanzando modificar turno");

        const url = 'http://localhost:8080/turnos/';

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
