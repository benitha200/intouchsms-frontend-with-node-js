import React, { useState } from 'react';
import axios from 'axios';

const Loginn = () => {

    const [username, setUsername] = useState('')
    const [password, setPassword] = useState('')

    const [token,setToken]=useState('')

    function login(e) {

        e.preventDefault();

        let headers = new Headers();
        let auth = btoa(`${username}:${password}`);

        // headers.append('Content-Type','multipart/form-data');
        headers.append('Accept','application/json');

        // var formdata = new FormData();
        // formdata.append("username", username);
        // formdata.append("password", password);

        var requestOptions = {
            method: 'POST',
            mode:'cors',
            headers: {
                'Authorization': `Basic ${auth}`,
              },
        };

        fetch('http://127.0.0.1:8000/api/generatetoken',requestOptions)
            .then(response => response.json())
            .then(result => setToken(result.token))
            .catch(error => console.log('error', error));

       alert(token)
    }


    return (
        <div>
            <h1 className='offset-sm-6'>Login</h1>
            <form className='col-sm-6 offset-sm-3' encType='multipart/form-data' onSubmit={login}>
                <input type="text" className='form-control' name="username" value={username} onChange={(e) => setUsername(e.target.value)} />
                <input type="password" className='form-control' name="password" value={password} onChange={(e) => setPassword(e.target.value)} autoComplete="true"/>
                <button className='btn btn-primary' type="submit">Submit</button>
            </form>

        </div>
    )
}

export default Loginn