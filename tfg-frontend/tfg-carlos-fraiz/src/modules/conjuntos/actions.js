import backend from '../../backend';
import * as actionTypes from './actionTypes';



export const findConjunto = idConjunto => dispatch => {

    dispatch(clearConjunto());
    backend.conjuntoService.verDetallesConjunto(idConjunto, conjunto => {
        dispatch(findConjuntoCompleted(conjunto));
        });
}   

export const getCuentasConjunto = idConjunto => dispatch => {

    dispatch(clearCuentas());
    backend.conjuntoService.verCuentasConjunto(idConjunto, cuentas => {
        dispatch(cuentasConjuntoCompleted(cuentas));
        });
}   


export const getInteraccionesConjunto = idConjunto => dispatch => {

    dispatch(clearInteracciones());
    backend.conjuntoService.verInteraccionesConjunto(idConjunto, interacciones => {
        dispatch(interaccionesConjuntoCompleted(interacciones));
        });
}   

const clearInteracciones = () => ({
    type: actionTypes.CLEAR_INTERACCIONES
});

export const filtrarCuentasHumanas = (idConjunto, porcentajeHumano) => dispatch => {
    dispatch(clearCuentas());
    backend.conjuntoService.verCuentasConFiltroHumano(idConjunto, porcentajeHumano, cuentas => {
        dispatch(cuentasConjuntoCompleted(cuentas));
        });
}   

export const filtrarCuentasBot = (idConjunto, porcentajeBot) => dispatch => {
    dispatch(clearCuentas());
    backend.conjuntoService.verCuentasConFiltroBot(idConjunto, porcentajeBot, cuentas => {
        dispatch(cuentasConjuntoCompleted(cuentas));
        });
}   

export const filtrarCuentas = (idConjunto, porcentajeHumano, porcentajeBot) => dispatch => {
    dispatch(clearCuentas());
    backend.conjuntoService.verCuentasConFiltro(idConjunto, porcentajeHumano, porcentajeBot, cuentas => {
        dispatch(cuentasConjuntoCompleted(cuentas));
        });
}   

const clearConjuntos = () => ({
    type: actionTypes.CLEAR_CONJUNTOS_LIST
});

const listarConjuntosCompleted = listaConjuntos => ({
    type: actionTypes.LISTAR_CONJUNTOS_COMPLETED,
    listaConjuntos
});

export const getConjuntos = page => dispatch => {
    dispatch(clearConjuntos());
    backend.conjuntoService.listaConjuntos(page, 
        result => dispatch(listarConjuntosCompleted({page, result})));
}



export const previousGetConjuntosResultPage = page =>
    getConjuntos({page: page-1});

export const nextGetConjuntosResultPage = page =>
    getConjuntos({page: page+1});


const findConjuntoCompleted = conjunto => ({
    type: actionTypes.FIND_CONJUNTO_COMPLETED,
    conjunto
});

const cuentasConjuntoCompleted = cuentas => ({
    type: actionTypes.CUENTAS_CONJUNTO_COMPLETED,
    cuentas
});


const interaccionesConjuntoCompleted = interacciones => ({
    type: actionTypes.INTERACCIONES_CONJUNTO_COMPLETED,
    interacciones
});

export const clearConjunto = () => ({
    type: actionTypes.CLEAR_CONJUNTO
});

export const clearCuentas = () => ({
    type: actionTypes.CLEAR_CUENTAS
});


