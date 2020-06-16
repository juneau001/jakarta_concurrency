# To build docker image, run the following:
docker build -t rosterservice:1.0 .
# To run docker image on port 8095, run the following:
docker run -it -p 8095:8080 rosterservice:1.0
