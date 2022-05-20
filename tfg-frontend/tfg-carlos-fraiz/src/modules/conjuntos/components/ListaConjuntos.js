import React from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage} from 'react-intl';

import * as actions from '../actions';
import * as selectors from '../selectors';
import {Pager} from '../../common';
import Conjuntos from './Conjuntos';

const ListaConjuntos = () => {
    
    const listaConjuntos = useSelector(selectors.getConjuntos);
    const dispatch = useDispatch();


    if (!listaConjuntos) {
        return null;
    }

    if (listaConjuntos.result.items.length === 0) {
        return (
            <div className="alert alert-info" role="alert">
                <FormattedMessage id='project.shopping.FindOrdersResult.noOrders'/>
            </div>
        );
    }

    return (

        <div>
            <Conjuntos conjuntos={listaConjuntos.result.items}/>
            <Pager 
                back={{
                    enabled: listaConjuntos.page >= 1,
                    onClick: () => dispatch(actions.previousGetConjuntosResultPage(listaConjuntos.page))}}
                next={{
                    enabled: listaConjuntos.result.existMoreItems,
                    onClick: () => dispatch(actions.nextGetConjuntosResultPage(listaConjuntos.page))}}/>

        </div>

    );

}

export default ListaConjuntos;