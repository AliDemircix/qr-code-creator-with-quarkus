#!/bin/bash

# Create the resources directory if it doesn't exist
mkdir -p src/main/resources/META-INF/resources

# Generate private key
openssl genrsa -out src/main/resources/META-INF/resources/privateKey.pem 2048

# Generate public key from private key
openssl rsa -in src/main/resources/META-INF/resources/privateKey.pem -pubout -out src/main/resources/META-INF/resources/publicKey.pem

echo "JWT keys generated successfully!"
echo "Private key: src/main/resources/META-INF/resources/privateKey.pem"
echo "Public key: src/main/resources/META-INF/resources/publicKey.pem"
