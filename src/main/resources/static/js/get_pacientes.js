window.addEventListener("load", function () {
  const botonListadoPacientes = document.querySelector("#listado-pacientes");

  botonListadoPacientes.addEventListener("click", function (event) {
    fetch("http://localhost:8080/pacientes")
      .then((respuesta) => respuesta.json())
      .then((listadoPacientes) => console.log(listadoPacientes));
  });
});
