package server.server;

import server.client.ClientController;

import java.util.ArrayList;
import java.util.List;

public class ServerController {
    private boolean work;
    private ServerView serverView;
    private List<ClientController> clientControllerList;
    private Repository<String> repository;

    public ServerController(ServerView serverView, Repository<String> repository) {
        this.repository = repository;
        this.serverView = serverView;
        clientControllerList = new ArrayList<>();
        serverView.setServerController(this);
    }

    public void start() {
        if(work) {
            showOnWindow("Сервер уже запущен!");
        } else {
            work = true;
            showOnWindow("Сервер запущен");
        }
    }

    public void stop() {
        if(!work) {
            showOnWindow("Сервер уже остановлен");
        }
        else {
            work = false;
            while (!clientControllerList.isEmpty()) {
                disconnectUser(clientControllerList.get(clientControllerList.size() - 1));
            }
        }
    }

    public void disconnectUser(ClientController clientController) {
        clientControllerList.remove(clientController);
        if (clientController != null) {
            clientController.disconnectedFromServer();
            showOnWindow(clientController.getName() + " отключен от сервера");
        }
    }

    public boolean connectUser(ClientController clientController) {
        if(!work) {
            return false;
        }
        clientControllerList.add(clientController);
        showOnWindow(clientController.getName() + " подключен к серверу");
        return true;
    }

    public void message(String message) {
        if(!work) {
            return;
        }
        showOnWindow(message);
        answerAll(message);
        addMessageInHistory(message);
    }

    public String getHistory() {
        return repository.load();
    }

    private void answerAll(String message) {
        for(ClientController clientController : clientControllerList) {
            clientController.answerFromServer(message);
        }
    }

    private void showOnWindow(String message) {
        serverView.showMessage(message + "\n");
    }

    private void addMessageInHistory(String message) {
        repository.save(message);
    }

}
