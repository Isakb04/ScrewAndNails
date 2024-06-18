package org.nailsandscrews;

public abstract class BaseController {

    protected SceneController sceneController;

    //to allow for inheritance
    public BaseController() {
        this.sceneController = SceneController.getInstance();
    }
}