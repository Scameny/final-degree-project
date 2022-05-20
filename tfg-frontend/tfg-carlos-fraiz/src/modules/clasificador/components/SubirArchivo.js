import React, { useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { FormattedMessage } from 'react-intl';
import { useHistory } from 'react-router-dom';
import { Errors } from '../../common';


import * as actions from '../actions';

const SubirArchivo = () => {


    const dispatch = useDispatch();
    const history = useHistory();
    const [file, setFile] = useState(null);
    const [nombre, setNombre] = useState('');
    const [descripcion, setDescripcion] = useState('');
    const [backendErrors, setBackendErrors] = useState(null);

    let form;



    const handleSubmit = event => {

        event.preventDefault();

        if (form.checkValidity()) {

            dispatch(actions.subirArchivo(file,
                nombre.trim(),
                descripcion.trim(),
                (conjunto) => history.push(`/conjunto/${conjunto.id}`),

                errors => setBackendErrors(errors)));

        } else {
            setBackendErrors(null);
            form.classList.add('was-validated');
        }

    }

    return (

        <div>
            <Errors errors={backendErrors}
                onClose={() => setBackendErrors(null)} />
            <div className="card bg-light border-dark">
                <h5 className="card-header">
                    <FormattedMessage id="project.clasificador.field.title" />
                </h5>
                <div className="card-body">
                    <form ref={node => form = node}
                        className="needs-validation" noValidate
                        onSubmit={(e) => handleSubmit(e)}>
                        <div className="form-group row">
                            <label htmlFor="nombre" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.clasificador.field.nombre" />
                            </label>
                            <div className="col-md-4">
                                <input type="text" id="nombre" className="form-control"
                                    value={nombre}
                                    onChange={e => setNombre(e.target.value)}
                                    autoFocus
                                    required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="descripcion" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.clasificador.field.descripcion" />
                            </label>
                            <div className="col-md-4">
                                <textarea type="text" id="descripcion" className="form-control"
                                    value={descripcion}
                                    onChange={e => setDescripcion(e.target.value)}
                                    required
                                    rows="3" />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <label htmlFor="file" className="col-md-3 col-form-label">
                                <FormattedMessage id="project.clasificador.field.file" />
                            </label>
                            <div className="col-md-4">
                                <input type="file" id="file" className=""
                                    onChange={e => setFile(e.target.files[0])}
                                    required />
                                <div className="invalid-feedback">
                                    <FormattedMessage id='project.global.validator.required' />
                                </div>
                            </div>
                        </div>
                        <div className="form-group row">
                            <div className="offset-md-3 col-md-1">
                                <button type="submit" className="btn btn-primary">
                                    <FormattedMessage id="project.global.buttons.upload" />
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>

    );

}
export default SubirArchivo;