# MoTiCPS: Energy Optimization on Multi-Objective Task Scheduling in IoT Integrated Cyber Physical Systems

African Vulture Optimization Algorithm implemented in Java and used as the task scheduler of the iFogsim.</br>


## Metaheuristics
1. African Vultures Optimization Algorithm (AVOA): A new nature-inspired metaheuristic algorithm for global optimization problems. </br>
   Benyamin Abdollahzadeh, Farhad Soleimanian Gharehchopogh, Seyedali Mirjalili https://doi.org/10.1016/j.cie.2021.107408 </br>
   Matlab implementation: https://www.mathworks.com/matlabcentral/fileexchange/94820-african-vultures-optimization-algorithm
To create a README file for your GitHub repository based on the paper titled "MoTiCPS: Energy Optimization on Multi-Objective Task Scheduling in IoT-Integrated Cyber-Physical Systems," here is a draft that you can use:


## Overview

This repository contains the implementation and associated resources for the paper **"MoTiCPS: Energy Optimization on Multi-Objective Task Scheduling in IoT-Integrated Cyber-Physical Systems"** by Abolfazl Younesi, Elyas Oustad, Mohsen Ansari, Mohammad Abolnejadian, and Alireza Ejlali, published in *IEEE Transactions on Sustainable Computing* in August 2024.

MoTiCPS introduces a novel approach for task scheduling and resource allocation in fog computing environments, designed explicitly for IoT-integrated cyber-physical systems (CPS). The method leverages the Osprey Optimization Algorithm (OOA) to improve task reliability, balance resource use across edge devices, and optimize the performance of fog nodes under real-time constraints.

## Key Features

- **Multi-Objective Optimization**: MoTiCPS optimizes for multiple objectives, including energy consumption, task makespan, and reliability.
- **Task Scheduling Algorithm**: A novel scheduling algorithm based on the Osprey Optimization Algorithm (OOA) ensures efficient task execution and resource management.
- **Reliability Enhancement**: The method employs a primary/backup fault-tolerance technique to enhance task reliability and system robustness.
- **Energy Efficiency**: The approach significantly reduces energy consumption in IoT-integrated CPS, making it suitable for energy-constrained environments.


## Requirements
- **iFogSim**: Some simulations may require the iFogSim simulator for fog computing, a Java-based tool.

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/MoTiCPS.git
   ```
2. Navigate to the project directory:
   ```bash
   cd MoTiCPS
   ```

You can adjust the configurations in the JSON file to change the parameters such as the number of tasks, fog nodes, energy settings, etc.


## Citation

If you use this code or any part of this work, please cite the original paper:

```
@article{Younesi2024MoTiCPS,
  title={MoTiCPS: Energy Optimization on Multi-Objective Task Scheduling in IoT-Integrated Cyber-Physical Systems},
  author={Abolfazl Younesi and Elyas Oustad and Mohsen Ansari and Mohammad Abolnejadian and Alireza Ejlali},
  journal={IEEE Transactions on Sustainable Computing},
  year={2024},
  volume={X},
  number={X},
  pages={1-10},
  doi={10.1109/TSUSC.2024.XXXXXXX}
}
```



## Contact

For any inquiries, please contact the corresponding author Mohsen Ansari at [abolfazl.yunesi@sharif.edu](mailto:abolfazl.yunesi@sharif.edu).

---

You can customize the content as needed and make sure that all links and paths are correct according to your project's structure.
