import React, { useState } from 'react'
import './Login.css'

import axios from 'axios';
// import heroImg from '../../../assets/images/3.jpg'
// import { Form } from 'react-router-dom'
import { Form, Button, FormGroup, Input } from 'reactstrap'
import { useNavigate } from 'react-router-dom'
import { BsKeyFill, BsPerson, BsPersonFill } from 'react-icons/bs'
import { InputGroup } from 'react-bootstrap'

const Login = ({setToken}) => {

  const [username, setUsername] = useState('intouch.sendsms')
  const [password, setPassword] = useState('intouch.sendsms')

  // const [token,setToken]=useState('')

  function login(e) {

      e.preventDefault();

      let headers = new Headers();
      let auth = btoa(`${username}:${password}`);

     
      headers.append('Accept','application/json');
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

    //  alert(setToken)
  }


return (
  <div>
    {/* <div className='hero-img-section'>
             <img className='hero-img' src={heroImg}/>
        </div> */}

    <div className='hero-content'>

      <div className='content-left'>

        <p className='content-header'>SMS Gateway</p>
        <span>Your Customer Engagement Platform</span>

        <button className='hero-button animated bounce'>Sign Up and Start Messaging</button>

        <span>
          With simple API intergration and cost-effective solutions,<br /> we connect you to your customers
        </span>
      </div>
      <div className='content-right'>
        <div className='login-form'>
          <span className='form-title'>LOG IN </span>
          <hr color='white' />
          {/* <Form onSubmit={onSubmit}> */}
          <form encType='multipart/form-data' onSubmit={login}>
            <FormGroup>
              <InputGroup>
                <InputGroup.Text>
                  <BsPersonFill />
                </InputGroup.Text>

                <Input
                  type='text'
                  className='form-control'
                  placeholder='Username'
                  name='username'
                  // onChange={(e)=>handle(e)}
                  onChange={(e) => setUsername(e.target.value)}
                  value={username}
                />
              </InputGroup>

            </FormGroup>
            <FormGroup>
              <InputGroup>
                <InputGroup.Text>
                  <BsKeyFill />
                </InputGroup.Text>
                <Input
                  type='password'
                  className="form-control"
                  placeholder='Password'
                  name='password'
                  // onChange={(e)=>handle(e)}
                  onChange={(e) => setPassword(e.target.value)}
                  value={password}
                />
              </InputGroup>

            </FormGroup>
            <FormGroup>
              <button type="submit" className='app-buttons text-dark'>
                Log In
              </button>
            </FormGroup>

            <FormGroup>
              <span className='text-light'> Don't have an account ? <a href='/createaccount'> Create Account</a></span>
            </FormGroup>
          </form>
        </div>
      </div>

    </div>
  </div>
)
}

export default Login