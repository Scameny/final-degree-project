import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    conjunto: null,
    cuentas: null,
    listaConjuntos: null,
    interacciones: null
};

const conjunto = (state = initialState.conjunto, action) => {

    switch (action.type) {

        case actionTypes.FIND_CONJUNTO_COMPLETED:
            return action.conjunto;

        case actionTypes.CLEAR_CONJUNTO:
            return initialState.conjunto;

        default:
            return state;

    }

}

const interacciones = (state = initialState.conjunto, action) => {

    switch (action.type) {

        case actionTypes.INTERACCIONES_CONJUNTO_COMPLETED:
            return action.interacciones;

        case actionTypes.CLEAR_INTERACCIONES:
            return initialState.interacciones;

        default:
            return state;

    }

}


const listaConjuntos = (state = initialState.listaConjuntos, action) => {

    switch (action.type) {

        case actionTypes.LISTAR_CONJUNTOS_COMPLETED:
            return action.listaConjuntos;

        case actionTypes.CLEAR_CONJUNTOS_LIST:
            return initialState.listaConjuntos;

        default:
            return state;

    }

}

const cuentas = (state = initialState.cuentas, action) => {

    switch (action.type) {

        case actionTypes.CUENTAS_CONJUNTO_COMPLETED:
            return action.cuentas;

        case actionTypes.CLEAR_CUENTAS:
            return initialState.cuentas;

        default:
            return state;

    }

}


const reducer = combineReducers({
    conjunto,
    listaConjuntos,
    cuentas,
    interacciones
});

export default reducer;
