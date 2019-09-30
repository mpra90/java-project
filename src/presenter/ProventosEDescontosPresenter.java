/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import view.ProventosEDescontosView;

/**
 *
 * @author marcos
 */
public enum ProventosEDescontosPresenter {
    INSTANCE;
    
    ProventosEDescontosView view;
    
    private ProventosEDescontosPresenter() {
        view = new ProventosEDescontosView();
        TelaPrincipalPresenter.INSTANCE.addView(view);
        initListeners();
    }

    private void initListeners() {
        view.getCbTipo().addActionListener((e) -> {
        });
        
        view.getCbSelecao().addActionListener((e) -> {
        });
        
        view.getBtnConfirmar().addActionListener((e) -> {
        });
        
        view.getBtnFechar().addActionListener((e) -> {
            view.setVisible(false);
        });
    }
    
    public void confTela() {
        view.setVisible(true);
    }
}
