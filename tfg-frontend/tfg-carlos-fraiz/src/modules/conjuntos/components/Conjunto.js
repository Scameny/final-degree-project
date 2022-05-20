import React, {useEffect} from 'react';
import {useSelector, useDispatch} from 'react-redux';
import {FormattedMessage, FormattedDate} from 'react-intl';
import {useParams, useHistory} from 'react-router-dom';


import * as actions from '../actions';
import * as selectors from '../selectors';
import {BackLink} from '../../common';


const Conjunto = () => {
    
    const {id} = useParams();
    const conjunto = useSelector(selectors.getConjunto);
    const dispatch = useDispatch();
    const history = useHistory();


    useEffect(() => {

        if (!Number.isNaN(id) && (!conjunto || conjunto.id!=id)) {   
            dispatch(actions.findConjunto(id));
        }

    }, [id, dispatch]);

    const redHexagonal = event => {
        event.preventDefault();
        dispatch(actions.getCuentasConjunto(id));
        history.push(`/conjunto/${conjunto.id}/hexagonal`);
    }

    const grafo = event => {
        event.preventDefault();
        dispatch(actions.getCuentasConjunto(id));
        dispatch(actions.getInteraccionesConjunto(id));
        history.push(`/conjunto/${conjunto.id}/grafo`);
    }

    if (!conjunto) {
        return null;
    }

    return (

        <div>
            <div className="row d-flex d-flex d-flex justify-content-between">
                <BackLink/>
                <div className="dropdown">
                        <button className="btn btn-secondary dropdown-toggle " type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <FormattedMessage id='project.conjunto.button.infovis' /> 
                        </button>
                        <div className="dropdown-menu" aria-labelledby="dropdownMenuButton">
                            <button className="dropdown-item" onClick={redHexagonal}>
                                <FormattedMessage id="project.conjunto.button.redHexagonal"/>
                            </button>
                            <button className="dropdown-item" onClick={grafo}>
                                <FormattedMessage id="project.conjunto.button.grafo"/>
                            </button>
                        </div>
                </div>
            </div>

            <div>
                <div className="card text-center w-100 p-4">
                    <div className="card-body">
                        <h5 className="card-title">
                            <FormattedMessage id='project.conjunto.fields.nombreConjunto' />: {conjunto.nombre}
                        </h5>
                        <h6 className="card-subtitle text-muted">
                            <FormattedDate value={new Date(conjunto.fechaCreacion)} />
                        </h6>
                        <p className="card-text">
                            <FormattedMessage id='project.conjunto.fields.nombreAutor' /> {conjunto.autorUsername}
                        </p>
                        <p className="card-text">
                          {conjunto.descripcion}
                        </p>
                    </div>
                </div>
            </div>
              

        </div>

    );

}

export default Conjunto;