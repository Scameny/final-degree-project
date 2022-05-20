import {combineReducers} from 'redux';

import * as actionTypes from './actionTypes';

const initialState = {
    conjunto: null
};

const conjunto = (state = initialState.conjunto, action) => {

    switch (action.type) {

        case actionTypes.FILE_UPLOADED:
            return action.conjunto;

        default:
            return state;

    }

}

const reducer = combineReducers({
    conjunto
});

export default reducer;


