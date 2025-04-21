# BCI

Microservicio prueba para BCI. Es un microservicio que registra usuario a una base de datos h2 

# Ownership
| Rol               |     Nombre                                        |
|---                |---                                                |
| Arquitecto        |  Francisco Varas                                  |
| Líder técnico     |  Francisco Varas                                  |
| Product Owner     |  Francisco Varas                                  |

#Funcionamiento

levanta de manera local en el puerto 8080, tiene un solo endpoint que registra usuario de tipo Post . Cada vez que inicia sesión levanta la bd h2 
se insertan datos dummy

#Swagger
http://localhost:8080/swagger-ui/index.html

#BD
http://localhost:8080/h2-console/login.jsp?jsessionid=6a1b5ec3b8668cb4f7ebde87d9cb7516 

#Body
{
"nombre": "Juan Rodriguez",
"correo": "juan@rodriguez.org",
"contraseña": "hunter2",
"telefonos": [
        {
        "numero": "1234567",
        "codigoCiudad": "1",
        "codigoPais": "57"
        }
    ]
} 

# End Points

### 📘 Endpoints del Servicio de Usuarios

| Nombre              | Ruta                                 | Método HTTP | Descripción                                                                 |
|---------------------|--------------------------------------|-------------|-----------------------------------------------------------------------------|
| registerUsuario     | `http://localhost:8080/usuario`      | POST        | Registra un nuevo usuario en el sistema.                                   |
| getAllUsuarios      | `http://localhost:8080/usuario`      | GET         | Obtiene la lista completa de usuarios registrados.                         |
| getUsuarioById      | `http://localhost:8080/usuario/{id}` | GET         | Obtiene la información de un usuario según su UUID.                        |
| updateUsuario       | `http://localhost:8080/usuario/{id}` | PUT         | Actualiza completamente la información de un usuario existente.            |
| patchUsuario        | `http://localhost:8080/usuario/{id}` | PATCH       | Actualiza parcialmente los campos de un usuario específico.                |
| deleteUsuario       | `http://localhost:8080/usuario/{id}` | DELETE      | Elimina un usuario del sistema usando su UUID.                             |


