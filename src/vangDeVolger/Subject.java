package vangDeVolger;

@SuppressWarnings("unused")
interface Subject {

    //we don't need the unregister at present, but we included it for good measure
    //perhaps in the future we will need it
    void register(Observer obj);

    void unregister(Observer obj);

    void notifyObservers(Object changedObject);

}
