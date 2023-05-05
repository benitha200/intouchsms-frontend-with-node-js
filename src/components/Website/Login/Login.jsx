import { useState } from 'react'
import './Login.css'
import { API_URL } from '../../../Constants/Index';
import bulk from '../../../assets/images/bulk.png';
import { Fade } from 'react-reveal';

// import axios from 'axios';
// // import heroImg from '../../../assets/images/3.jpg'
// // import { Form } from 'react-router-dom'
// import { Form, Button, FormGroup, Input } from 'reactstrap'
// import { useNavigate } from 'react-router-dom'
// import { BsKeyFill, BsPerson, BsPersonFill } from 'react-icons/bs'
// import { InputGroup } from 'react-bootstrap'
// import { useEffect } from 'react';
// import bulkMessage from '../../../assets/images/BulkMessages.gif';


const Login = ({ setToken }) => {

  const [username, setUsername] = useState('')
  const [password, setPassword] = useState('')

  // const [token,setToken]=useState('')

  function login(e) {

    e.preventDefault();

    let headers = new Headers();
    let auth = btoa(`${username}:${password}`);


    headers.append('Accept', 'application/json');
    var requestOptions = {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Authorization': `Basic ${auth}`,
      },
    };



    fetch('/generatetoken', requestOptions)
      .then(response => response.json())
      .then(data => {
        const token = data.token;
        localStorage.setItem('token', token);
        setToken(token);
      })
      .catch(error => console.log('error', error));
    // .then(result => setToken(result.token))



    console.log(setToken)
  }


  // const [username, setUsername] = useState('')
  // const [password, setPassword] = useState('')
  // const [navigate, useNavigate] = useState()



  //   // var formdata = new FormData();

  //   // let auth = btoa(`${username}:${password}`);

  //   // var requestOptions = {
  //   //   method: 'GET',
  //   //   mode:'cors',
  //   //   headers: {
  //   //     'Authorization' :`Basic ${auth}`,
  //   //   },
  //   //   body: formdata,
  //   //   redirect: 'follow'
  //   // };

  //   // fetch("http://127.0.0.1:8000/api/generatetoken", requestOptions)
  //   //   .then(response => response.json())
  //   //   .then(data => {
  //   //         const token = data.token;
  //   //         localStorage.setItem('token', token);
  //   //         setToken(token);
  //   //       })
  //   //   .catch(error => console.log('error', error));

  //   // e.preventDefault();

  //   // let headers = new Headers();
  //   // let auth = btoa(`${username}:${password}`);


  //   // // headers.append('Accept', 'application/json');
  //   // var requestOptions = {
  //   //   method: 'POST',
  //   //   mode: 'cors',
  //   //   headers: {
  //   //     'Authorization' :`Basic ${auth}`,
  //   //   },
  //   // };





  //   // fetch(API_URL + 'generatetoken', requestOptions)
  //   //   .then(response => response.json())
  //   //   .then(data => {
  //   //     const token = data.token;
  //   //     localStorage.setItem('token', token);
  //   //     setToken(token);
  //   //   })
  //   //   // .then(result => console.log(result))
  //   //   // .then(result=>console.log(localStorage.setItem("authtoken",result.token)))
  //   //   .catch(error => {
  //   //     console.log('error', error)
  //   //   });

  //   //   navigate("/")

  //   console.log(setToken)
  // }


  return (
    <>
    <div className='login-page h-100'>
      {/* <div className='hero-img-section'>
             <img className='hero-img' src={heroImg}/>
        </div> */}

      <div className='login-container d-flex flex-row pt-5 me-1 mb-5'>

        {/* {/* <div className='left pt-0 gap-4 pl-0'> */}
        <div className='w-50'>
          <Fade left>
          <img src={bulk} className=" bulk-image"/>
          </Fade>
        </div>


        <div className='form-holder shadow p-3 mb-5 bg-white h-100 gap-2 pb-5 mt-5 pull-right'>
          {/* //  <div className='login-form'> */}
          <div className='pt-5'>
            <span className='form-title font-weight-bold h4'>Login </span>
          </div>
          <hr color='grey' />

          <form className='d-flex flex-column gap-3' encType='multipart/form-data' method='GET' onSubmit={login}>
            <div>
              <input
                type="text"
                placeholder='Username'
                className='form-control border-bottom rounded-0'
                onChange={(e) => setUsername(e.target.value)}
                value={username}
              /></div>
            <div>
              <input
                type="password"
                placeholder='password'
                className='form-control border-bottom rounded-0 width-25'
                onChange={(e) => setPassword(e.target.value)}
              />
            </div>

            <button type="submit" className='app-buttons w-100 text-dark'> Log In </button>

          </form>

          <hr color="grey" />
          <span className='text-dark'> Don't have an account ? <a href='/createaccount'> Create Account</a></span>

          {/* </div> */}


        </div>


      </div>
    </div>
    </>
    
  )
}

export default Login