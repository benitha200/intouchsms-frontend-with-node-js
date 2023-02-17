import React, { useState } from 'react'
import './Login.css'

import axios from 'axios'
// import heroImg from '../../../assets/images/3.jpg'
// import { Form } from 'react-router-dom'
import { Form, Button, FormGroup, Input } from 'reactstrap'
import { useNavigate } from 'react-router-dom'
const Login = () => {

    let navigate = useNavigate();

    const[username,setUsername] = useState(null)
    const[password,setPassword]= useState(null)

    const login= async ()=>{
        let formField= new FormData()
        formField.append('username',username)
        formField.append('password',password)

        await axios({
            method:'post',
            url:'http://localhost:8000/authenticatelogin/',
            data:formField
            }).then(response=>{
                console.log(response.data)
                navigate('/homepage')
            })
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
            With simple API intergration and cost-effective solutions,<br/> we connect you to your customers
          </span>
        </div>
        <div className='content-right'>
          <div className='login-form'>
            <span className='form-title'>LOG IN </span>
             <Form>
                <FormGroup>
                    <Input
                      type='text'
                      className='text-input'
                      placeholder='username'
                      name='username'
                      value={username}
                      onChange={(e)=>setUsername(e.target.value)}

                    />  
                  
                </FormGroup>
                <FormGroup>
                    <Input
                    type='password'
                    className='text-input'
                    placeholder='password'
                    name='password'
                    value={password}
                    onChange={(e)=>setPassword(e.target.value)}
                    />
                </FormGroup>
                <FormGroup>
                    <Button className='form-button' onClick={login}>
                        Log In
                    </Button>
                </FormGroup>
             </Form>
          </div>
        </div>

      </div>
    </div>
  )
}

export default Login