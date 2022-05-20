import {combineReducers} from 'redux';

import app from '../modules/app';
import users from '../modules/users';
import clasificador from '../modules/clasificador';
import conjuntos from '../modules/conjuntos';



const rootReducer = combineReducers({
    app: app.reducer,
    users: users.reducer,
    conjuntos: conjuntos.reducer,
    clasificador: clasificador.reducer
});


export default rootReducer;
