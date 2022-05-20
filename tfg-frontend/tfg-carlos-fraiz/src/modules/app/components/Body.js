import React from 'react';
import {useSelector} from 'react-redux';
import {Route, Switch} from 'react-router-dom';

import AppGlobalComponents from './AppGlobalComponents';
import Home from './Home';
import {Login, SignUp, UpdateProfile, ChangePassword, Logout} from '../../users';
import {SubirArchivo} from '../../clasificador';
import {Conjunto, RedHexagonal,ListaConjuntos, ListarConjuntos, GrafoDeCuentas  } from '../../conjuntos';


import users from '../../users';

const Body = () => {

    const loggedIn = useSelector(users.selectors.isLoggedIn);
    
   return (

        <div className="container">
            <br/>
            <AppGlobalComponents/>
            <Switch>
                <Route exact path="/"><Home/></Route>
                {loggedIn && <Route exact path="/users/update-profile"><UpdateProfile/></Route>}
                {loggedIn && <Route exact path="/users/change-password"><ChangePassword/></Route>}
                {loggedIn && <Route exact path="/users/logout"><Logout/></Route>}
                {loggedIn && <Route exact path="/clasificacion/upload"><SubirArchivo/></Route>}
                {loggedIn && <Route exact path="/conjunto/:id"><Conjunto/></Route>}
                {loggedIn && <Route exact path="/conjunto/:id/hexagonal"><RedHexagonal/></Route>}
                {loggedIn && <Route exact path="/conjuntos/lista"><ListaConjuntos/></Route>}
                {loggedIn && <Route exact path="/conjuntos"><ListarConjuntos/></Route>}
                {loggedIn && <Route exact path="/conjunto/:id/grafo"><GrafoDeCuentas/></Route>}
                {!loggedIn && <Route exact path="/users/login"><Login/></Route>}
                {!loggedIn && <Route exact path="/users/signup"><SignUp/></Route>}
                <Route><Home/></Route>
            </Switch>
        </div>

    );

};

export default Body;
