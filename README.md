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
http://localhost:8080/swagger-ui.html

#BD
http://localhost:8080/h2-console/login.jsp?jsessionid=6a1b5ec3b8668cb4f7ebde87d9cb7516 

#Body
{
    "name": "Juan Rodriguez",
    "email": "juan@rodriguez.org",
    "password": "hunter2",
    "phones": [
        {
            "number": "1234567",
            "citycode": "1",
            "contrycode": "57"
        }
    ]
} 

# End Points
|
| Nombre          | Ruta                            |  Tipo  | Descripción       
| registerUsuario | http://localhost:8080/usuario   |  POST  | End point encardo de hacer lógica de registrar Usuario  |

http://localhost:8080/h2-console/login.jsp?jsessionid=6a1b5ec3b8668cb4f7ebde87d9cb7516 
