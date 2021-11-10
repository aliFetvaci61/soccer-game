// Insert Admin user
db.getCollection('users').insertOne(
{
    "username" : "admin",
    "email" : "admin",
    "password" : "$2a$10$PUplQHOa5p9Lb/RIAZOxu.7Um.VtRcGnCLqI0Ad17XLVofoNcp1NO",
    "roles" : "ROLE_ADMIN",
    "_class" : "com.alifetvaci.soccergame.models.User"
}
)