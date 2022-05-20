import {useEffect} from 'react';
import {useDispatch} from 'react-redux';
import {useHistory} from 'react-router-dom';

import * as actions from '../actions';

const ListarConjuntos = () => {

    const dispatch = useDispatch();
    const history = useHistory();

    useEffect(() => {

        dispatch(actions.getConjuntos(0));
        history.push('/conjuntos/lista');

    });

    return null;

}

export default ListarConjuntos;
