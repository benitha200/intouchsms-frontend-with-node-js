import {Paper, Typography } from '@mui/material'
import { Stack } from '@mui/system'
import { useEffect, useState } from 'react'
import './MyAccountInfo.css'
import Button from '@mui/material/Button'
import { TOKEN } from '../../../../../Constants/Index'
import axios from 'axios'

const MyAccountInfo = () => {

     const [token,setToken] = useState(TOKEN)
     const [balance,setBalance] = useState('')

     const getAccountInfo = async()=>{
        const response= await axios.post('https://intouchsms.co.rw/api/getmybalance',token);
        console.log(response.data)
        setBalance(response.data)
     }

     const getName = async()=>{
        const response = await axios.post('http://127.0.0.1:8000/api/getmyprofile',token);
        console.log(response)
     }

     useEffect(()=>{
        getName();
     },[]);
   
   




    return (
        <div>
            <Paper elevation={0} sx={{ width: '99%', height: '10rem', margin: '0.5rem', padding: '0rem' }}>

                <div className='container'>

                    <div className='container-left'>
                        <span>Welcome Benitha Louange</span>
                        <br/>
                        <span>Balance: {balance} credits</span>
                    </div>

                    
                       
                    
                        <Stack direction="row" gap={2}>
                            <button className='account-buttons'>Top Up</button>
                            <button className='account-buttons'>Logout</button>
                        </Stack>
                    



                </div>



            </Paper>
        </div>
    )
}

export default MyAccountInfo
