// src/DI/DependencyContainer.java
package DI;

import repository.interfaces.IPetDAO;
import repository.File.PetFileDAO;
import service.interfaces.IPetService;
import service.PetService;
import util.FileManage;
import util.interfaces.IFileManage;

public class DependencyContainer {
    private static IPetDAO petRepository;
    private static IPetService petService;
    private static IFileManage fileManage;

    public static IFileManage getFileManage() {
        if (fileManage == null) {
            fileManage = new FileManage();
        }
        return fileManage;
    }

    public static IPetDAO getPetRepository() {
        if (petRepository == null) {
            petRepository = new PetFileDAO(getFileManage());
        }
        return petRepository;
    }

    public static IPetService getPetService() {
        if (petService == null) {
            petService = new PetService(getPetRepository());
        }
        return petService;
    }

}