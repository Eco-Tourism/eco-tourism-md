# Eco Tourism

Sebelum di deploy
buat db dulu di local
test postman local

*home*
http://localhost:8080

*public*
http://localhost:8080/api/test/all

*register*
http://localhost:8080/api/auth/signup

contoh req body
```text
{
    "username":"test",
    "email": "test@gmail.com",
    "namalengkap": "testtheo",
    "alamat": "testalamattheo",
    "kontak": "test",
    "jeniskelamin": "L",
    "hobi": "testing",
    "password": "test"
}
```

*login*
http://localhost:8080/api/auth/signin

contoh req body
```text
{
    "username":"test",
    "password": "test"
}
```

*users*
http://localhost:8080/api/test/user

contoh header
contoh token dari login

```text
x-access-token;eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNjg2NTEyNDk1LCJleHAiOjE2ODY1OTg4OTV9.vt1fNAZpUVJpgkFKf9v3jEPVaKewBcnzLmVFCeqyZ2s
```

api setelah di deploy

*home*
http://34.101.127.157:8080

*public*
http://34.101.127.157:8080/api/test/all

*register*
http://34.101.127.157:8080/api/auth/signup

*login*
http://34.101.127.157:8080/api/auth/signin

*users*
http://34.101.127.157:8080/api/test/user

## Documentation
https://documenter.getpostman.com/view/27962810/2s93sf1qQa
