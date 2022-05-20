import React from 'react';
import {FormattedMessage} from 'react-intl';
import PropTypes from 'prop-types';
import ConjuntoLink from './ConjuntoLink'


const Conjuntos = ({conjuntos}) => (
    <table className="table table-striped table-hover">

        <thead>
            <tr>
                <th scope="col">
                    <FormattedMessage id='project.conjunto.fields.nombreConjunto'/>
                </th>
                <th scope="col">
                    <FormattedMessage id='project.conjunto.fields.nombreAutor'/>
                </th>
            </tr>
        </thead>

        <tbody>
            {conjuntos.map(conjunto => 
                <tr key={conjunto.id}>
                    <td><ConjuntoLink id={conjunto.id} name={conjunto.nombre}/></td>
                    <td>{conjunto.autorUsername}</td>
                </tr>
            )}
        </tbody>

    </table>
);

Conjuntos.propTypes = {
    conjuntos: PropTypes.array.isRequired
};

export default Conjuntos;
