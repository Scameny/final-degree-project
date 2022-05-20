import React, { useState } from 'react';
import {FormattedMessage} from 'react-intl';
import {useDispatch} from 'react-redux';
import {useParams} from 'react-router-dom';


import * as actions from '../actions';


const Filtro = () => {


    const [range, setRange] = useState(70);
    const [filter, setFilter] = useState('todo');
    const {id} = useParams();
    const dispatch = useDispatch();



    const handleSubmit = event => {
        event.preventDefault();
        if (filter === 'todo')
            dispatch(actions.filtrarCuentas(id, range/100, range/100));
        if (filter === 'bots')
            dispatch(actions.filtrarCuentasBot(id, range/100));
        if (filter === 'humanas')
            dispatch(actions.filtrarCuentasHumanas(id, range/100));
        
        
    }


    return (
        <div className="dropdown show dropleft">
        <a className="btn btn-secondary dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            <i className="fa fa-filter" aria-hidden="true"></i>
        </a>
        <div className="dropdown-menu">
        <form className="px-3 py-3" onSubmit={e => handleSubmit(e)}>
            <div className="form-group">
            <label>
            <FormattedMessage id='project.conjunto.filtro.filtroCuentas' />
            </label>
            <select className="custom-select" onChange={e => setFilter(e.target.value)} >

                <FormattedMessage id='project.conjunto.filtro.todaCuenta'>
                    {message => (<option selected value="todo">{message}</option>)}
                </FormattedMessage>

                <FormattedMessage id='project.conjunto.filtro.cuentasBots'>
                {message => (<option value="bots">{message}</option>)}
                </FormattedMessage>

                <FormattedMessage id='project.conjunto.filtro.cuentasHumanos'>
                {message => (<option value="humanas">{message}</option>)}
                </FormattedMessage>
                
                
            </select>


            <div class="dropdown-divider"></div>

            </div>
            <div className="form-group">
                <label>
                <FormattedMessage id='project.conjunto.filtro.porcentaje' />
                </label>
                <div className="form-row">
                    <div className="col">
                        <input
                        type="range" className="custom-range" id="porcentaje" min="50" max="100" step="10"
                        value={range} onChange={e => setRange(e.target.value)} />
                    </div>
                    <div className="col">
                        <input readOnly id="valorPorcentaje" value={range}/>
                    </div>
                </div>
            </div>
            <br/>
         <button type="submit" class="btn btn-primary">filtrar</button>
         </form>
        </div> 


        </div>
    );
};

export default Filtro;