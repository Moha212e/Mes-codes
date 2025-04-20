package org.example.view;

import org.example.model.entity.Client;

public interface ViewLocation {
    void showLocation(Client client);
    void showAddLocationForm();
    void showUpdateLocationForm();
    void showDeleteLocationForm();

}
