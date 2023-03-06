import React, { useEffect, useState } from 'react'
import './Header.css'
import { Stack } from '@mui/material'
import { TOKEN } from '../../../Constants/Index'
import axios from 'axios'

const Header = ({token}) => {

const [balance,setBalance] = useState('')

 var requestOptions = {
      method: 'POST',
      mode:'cors',
      headers: {
        'Authorization':`Token ${token}`
      }
    };
    
    fetch("http://127.0.0.1:8000/api/getmybalance", requestOptions)
      .then(response => response.text())
      .then(result => console.log(result))
      .catch(error => console.log('error', error));

    
    fetch("http://127.0.0.1:8000/api/getmyprofile",requestOptions)
    .then(response=>response.text())
    .then(result=>console.log(result))
    .catch(error=>console.log('error',error));
  
     



    
    return (
        <div className='header'>

            <div className='container'>

                <div className='app-title'>
                    <span className="app-main-header"> Intouchsms Communication Platform</span>

                </div>
                <div className='pt-2'>


                    <Stack direction="row" gap={2}>
                        <button className='account-buttons'>Top Up</button>
                        <button className='account-buttons'>Logout</button>
                    </Stack>

                    <div className='account-info'>

                
                        <span>Welcome Benitha Louange</span>
                        <br />
                        <span>Balance: wait credits</span>
                    </div>






                </div>



            </div>

        </div>

    )
}

export default Header
