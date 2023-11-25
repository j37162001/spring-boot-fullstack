// import UserProfile from "./UserProfile.jsx";
// import { useState, useEffect} from "react";

import { Wrap,WrapItem, Spinner,Text } from "@chakra-ui/react";
import SidebarWithHeader from "./components/shared/SideBar.jsx";
import { useEffect,useState} from "react";
import {getCustomers} from "./services/client.js";
import CardWithImage from "./components/Card.jsx";


const App = () => {
    const [customers,setCustomers] = useState([]);
    const[loading,setLoading]= useState(false);

    useEffect(()=>{
        setLoading(true);
        getCustomers().then(res=>{
            setCustomers(res.data)
        }).catch(err=>{
            console.log(err)
        }).finally(()=>{
            setLoading(false)
        })
    },[])

    if(loading){
        return (
        <SidebarWithHeader>
            <Spinner
                thickness='4px'
                speed='0.65s'
                emptyColor='gray.200'
                color='blue.500'
                size='xl'
            />
        </SidebarWithHeader>
        )
    }
    if(customers.length <=0){
        return(
        <SidebarWithHeader>
           <Text>No Customers available</Text>
        </SidebarWithHeader>
        )
    }
    return (
        <SidebarWithHeader>
            <Wrap justify={"center"} spacing={"30"}>
            {customers.map((customer,index)=> (
                <WrapItem key={index}>
                    <CardWithImage
                        {...customer}/>
                </WrapItem>
            ))}
            </Wrap>
        </SidebarWithHeader>
    )
}


// const users =[
//     {
//         name:"Jacky",
//         age:27,
//         gender:"MALE"
//     },
//     {
//         name:"Micky",
//         age:18,
//         gender:"FEMALE"
//     },
//     {
//         name:"John",
//         age:51,
//         gender:"MALE"
//     },
//     {
//         name:"Mike",
//         age:42,
//         gender:"MALE"
//     },
//     {
//         name:"Dora",
//         age:29,
//         gender:"FEMALE"
//     },
// ]

// const UserProfiles =({ users }) => (
//     <div>
//         {users.map((user,index)=>(
//             <UserProfile
//                 key={index}
//                 name={user.name}
//                 age={user.age}
//                 gender={user.gender}
//                 imageNumber={index}
//             />
//         ))}
//     </div>
// )
// function App() {
//
//     const [counter, setCounter] = useState(0);
//     const [isloading, setIsLoading] = useState(false);
//     useEffect(()=>{
//         setTimeout(()=>{
//             setIsLoading(false);
//         },4000)
//         return () => {
//             console.log("cleanup functions")
//         }
//     },[])
//
//     if(isloading){
//         return "loading...";
//     }
//   return (
//       <div>
//           <button onClick={()=>setCounter(prevCounter=>prevCounter+1)}>
//               Increment counter
//           </button>
//           <h1>{counter}</h1>
//           <UserProfiles users={users}/>
//       </div>
//   )
// }

export default App
