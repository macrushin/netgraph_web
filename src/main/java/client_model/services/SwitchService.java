package client_model.services;

import client_model.dao.SwitchDAO;
import client_model.model_ui.Switch;

public class SwitchService {

    private final SwitchDAO switchDAO = new SwitchDAO();

    public Switch findSwitch(int id) {
        return switchDAO.findById(id);
    }

    public void save(Switch swtch) {
        switchDAO.save(swtch);
    }

    public void update(Switch swtch) {
        switchDAO.update(swtch);
    }

    public void delete(Switch swtch) {
        switchDAO.delete(swtch);
    }


}
