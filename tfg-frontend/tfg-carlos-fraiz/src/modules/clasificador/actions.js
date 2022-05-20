import backend from '../../backend';
import * as actionTypes from './actionTypes';

const fileUpdated = conjunto => ({
    type: actionTypes.FILE_UPLOADED,
    conjunto
});

export const subirArchivo = (file, nombre, descripcion, onSuccess, onErrors) => dispatch =>
    backend.clasificadorService.subirArchivo(file, nombre, descripcion, conjunto => {
            dispatch(fileUpdated(conjunto));
            onSuccess(conjunto);
        },
        onErrors);