const getModuleState = state => state.conjuntos;


export const getConjunto = state =>
    getModuleState(state).conjunto;

export const getCuentas = state =>
    getModuleState(state).cuentas;

export const getInteracciones = state =>
    getModuleState(state).interacciones;


export const getConjuntos = state =>
    getModuleState(state).listaConjuntos;

