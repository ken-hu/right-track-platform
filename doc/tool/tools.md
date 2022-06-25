# Create jks file 
keytool -genkey -alias ken-jwt -keyalg RSA -keysize 1024 -keystore ken-jwt.jks -validity 365 -keypass 123456 -storepass 123456