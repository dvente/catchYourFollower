package vangDeVolger;

interface Observer {

    //pass an object because here we want to be able to pass both
    //strings and directions
    void update(Object changedObject);

}
