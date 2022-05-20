import React from 'react';
import PropTypes from 'prop-types';

import {Link} from 'react-router-dom';

const ConjuntoLink = ({id, name}) => {
    
    return (
        <Link to={`/conjunto/${id}`}>
            {name}
        </Link>
    );

}

COnjuntoLink.propTypes = {
    id: PropTypes.number.isRequired,
    name: PropTypes.string.isRequired,
};

export default ProductLink; 