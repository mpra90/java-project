/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presenter;

import view.InclusaoDeFaltasView;

/**
 *
 * @author marcos
 */
public enum InclusaoDeFaltasPresenter {
    INSTANCE;
    
    private InclusaoDeFaltasView view;
    
    private InclusaoDeFaltasPresenter() {
        view = new InclusaoDeFaltasView();
        TelaPrincipalPresenter.INSTANCE.addView(view);
        initListeners();
    }

    private void initListeners() {
        view.getTextData().addActionListener((e) -> {
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
